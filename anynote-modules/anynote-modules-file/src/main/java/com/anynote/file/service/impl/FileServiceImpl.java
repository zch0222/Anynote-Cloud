package com.anynote.file.service.impl;

import com.anynote.common.redis.constant.RedisKey;
import com.anynote.common.redis.service.RedisService;
import com.anynote.core.constant.CacheConstants;
import com.anynote.core.constant.RequestAttributesConstants;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ServletUtils;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.utils.UrlUtil;
import com.anynote.core.utils.file.FileUtils;
import com.anynote.file.api.model.bo.*;
import com.anynote.file.api.model.dto.CompleteUploadDTO;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.file.api.model.vo.OssSliceUploadChunkMarkVO;
import com.anynote.file.api.model.vo.OssSliceUploadComposeOV;
import com.anynote.file.api.model.vo.OssSliceUploadSignatureVO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.file.factory.FilePluginFactory;
import com.anynote.file.mapper.FileMapper;
import com.anynote.file.model.bo.OssObjectComposeResponse;
import com.anynote.file.plugin.FilePlugin;
import com.anynote.file.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 笔记图片文件服务
 * @author 称霸幼儿园
 */
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePO>
        implements FileService {


    @Autowired
    private FilePluginFactory filePluginFactory;

    @Resource
    private RedisService redisService;

//    @Override
//    public FileDTO upload(NoteImageUploadParam uploadParam) {
//        NoteImage noteImage = NoteImage.builder()
//                .originalFileName(uploadParam.getFile().getOriginalFilename())
//                .fileName(UUID.randomUUID().toString().replace("-", "") + "_" + uploadParam.getFile().getOriginalFilename())
//                .us
//                .build();
//        String url = filePluginFactory.filePlugin()
//                .multipartFileUpload(uploadParam.getFile(),
//                        StringUtils.format("note/{}", uploadParam.getNoteId()),
//                        noteImage.getFileName());
//
//        return null;
//    }


    @Override
    public FilePO upload(CommonsMultipartFile file, String path, Long userId, String uploadId, Integer source) {
        ServletUtils.setRequestAttributes(RequestAttributesConstants.FILE_UPLOAD_ID_KEY, uploadId);
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
        String url = filePluginFactory.filePlugin()
                .multipartFileUpload(file, path, fileName);
        Date date = new Date();
        FilePO filePO = null;
        try {
            filePO = FilePO.builder()
                    .hash(DigestUtils.sha512Hex(file.getInputStream()))
                    .originalFileName(file.getOriginalFilename())
                    .fileName(fileName)
                    .url(url)
                    .source(source)
                    .deleted(0)
                    .type(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))
                    .createBy(userId)
                    .createTime(date)
                    .updateBy(userId)
                    .updateTime(date)
                    .build();
        } catch (IOException e) {
            throw new BusinessException("计算文件Hash失败");
        }
        this.baseMapper.insert(filePO);
        return filePO;
    }

    @Override
    public UploadProgress getFileUploadProgress(String uploadId) {
        return redisService.getCacheObject(CacheConstants.FILE_UPLOAD_PROGRESS_KEY + uploadId);
    }

    @Override
    public HuaweiOBSTemporarySignature createHuaweiOBSTemporarySignature(String path, String fileName, Long expireSeconds,
                                                                         String contentType, Integer source) {
        Date date = new Date();
        FilePO filePO = FilePO.builder()
                .originalFileName(fileName)
                .fileName(UUID.randomUUID().toString().replace("-", "") + "." +
                        FileUtils.getFileExtension(fileName))
                .createBy(Long.valueOf(ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID)))
                .deleted(0)
                .createTime(date)
                .type(contentType)
                .source(source)
                .build();
        String uploadId = UUID.randomUUID().toString().replace("-", "");
        HuaweiOBSTemporarySignature huaweiOBSTemporarySignature = filePluginFactory
                .huaweiFilePlugin().createTemporarySignature(path,
                        filePO.getFileName(),
                        expireSeconds, contentType);
        huaweiOBSTemporarySignature.setUploadId(uploadId);
        filePO.setUrl(UrlUtil.removeAllParams(huaweiOBSTemporarySignature.getSignedUrl()));

        redisService.setCacheObject(CacheConstants.FILE_UPLOAD_ID +
                        ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID) + ":" + uploadId, filePO,
                expireSeconds + 20L, TimeUnit.SECONDS);
        return huaweiOBSTemporarySignature;
    }

    @Override
    public FilePO completeUpload(CompleteUploadDTO completeUploadDTO) {
        Date date = new Date();
        FilePO filePO = redisService.getCacheObject(CacheConstants.FILE_UPLOAD_ID +
                ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID) + ":" +
                completeUploadDTO.getUploadId());
        if (StringUtils.isNull(filePO)) {
            throw new BusinessException("上传超时");
        }
        filePO.setHash(completeUploadDTO.getHash());
        filePO.setUpdateTime(date);
        filePO.setUpdateBy(Long.valueOf(ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID)));
        this.baseMapper.insert(filePO);
        redisService.deleteObject(CacheConstants.FILE_UPLOAD_ID +
                ServletUtils.getRequestAttributes(SecurityConstants.DETAILS_USER_ID) + ":" +
                completeUploadDTO.getUploadId());
        return filePO;
    }

    @Override
    public FilePO getFileById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 获取分片上传任务Redis key
     * @param userId 用户id
     * @param uploadId 文件上传id
     * @return 分片上传任务Redis key
     */
    private String getOssSliceUploadTaskInfoKey(Long userId, String uploadId) {
        return StringUtils.format(RedisKey.OSS_SLICE_UPLOAD_TASK, userId, uploadId);
    }

    /**
     * 获取分片上传任务完成列表的key
     * @param uploadId 文件上传id
     * @return 分片上传任务完成列表的key
     */
    private String getOssSliceUploadTaskFinishedSliceIndexSetKey(String uploadId) {
        return StringUtils.format(RedisKey.OSS_SLICE_UPLOAD_TASK_FINISHED_SLICE_INDEX_SET, uploadId);
    }

    private String getOssSliceUploadChunkObjectName(OssSliceUploadTaskInfo ossSliceUploadTaskInfo, int chunkIndex) {
        return StringUtils.format("{}/{}_chunk_{}", ossSliceUploadTaskInfo.getChunkFolder(),
                ossSliceUploadTaskInfo.getFileInfo().getFileName(), chunkIndex);
    }

    @Override
    public OssSliceUploadTaskVO createOssSliceUploadTask(String path, String fileName,
                                                         String hash, Double fileSize,
                                                         String contentType, Integer source) {
        String uploadId = UUID.randomUUID().toString().replace("-", "");
        //Long userId = Long.valueOf(ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID));

        // debug
        Long userId = userId = 0L;

        Date date = new Date();
        FilePO filePO = FilePO.builder()
                .originalFileName(fileName)
                .fileName(UUID.randomUUID().toString().replace("-", "") + "." +
                        FileUtils.getFileExtension(fileName))
                .createBy(userId)
                .deleted(0)
                .createTime(date)
                .type(contentType)
                .source(source)
                .build();

        FilePlugin filePlugin = filePluginFactory.filePlugin();
        OssSliceUploadTaskInfo ossSliceUploadTaskInfo = filePlugin
                .createOssSliceUploadTask(hash, fileSize, path, StringUtils.format("{}/{}",
                        path, filePO.getFileName()), uploadId);
        ossSliceUploadTaskInfo.setFileInfo(filePO);
        ossSliceUploadTaskInfo.setUploadId(uploadId);
        redisService.setCacheObject(getOssSliceUploadTaskInfoKey(userId, uploadId), ossSliceUploadTaskInfo);
        redisService.addToSet(getOssSliceUploadTaskFinishedSliceIndexSetKey(uploadId), new HashSet<>());
        return OssSliceUploadTaskVO.builder()
                .originalFileName(fileName)
                .fileName(filePO.getFileName())
                .fileSize(fileSize)
                .uploadId(uploadId)
                .chunkSize(ossSliceUploadTaskInfo.getChunkSize())
                .totalChunk(ossSliceUploadTaskInfo.getTotalChunk())
                .build();
    }

    @Override
    public OssSliceUploadSignatureVO getOssSliceUploadSignature(String uploadId, List<Integer> chunkIndexList) {

        //Long userId = Long.valueOf(ServletUtils.getHeader(SecurityConstants.DETAILS_USER_ID));

        // debug
        Long userId = 0L;
        OssSliceUploadTaskInfo ossSliceUploadTaskInfo = redisService.getCacheObject(getOssSliceUploadTaskInfoKey(userId, uploadId));
        for (Integer chunkIndex : chunkIndexList) {
            if (chunkIndex <= 0 || chunkIndex > ossSliceUploadTaskInfo.getTotalChunk()) {
                log.error(StringUtils.format("ERROR CHUNK_INDEX: OssSliceUploadId: {}, TotalChunk: {}, chunkIndex: {}", uploadId,
                        ossSliceUploadTaskInfo.getTotalChunk(), chunkIndex));
                throw new BusinessException("获取分片签名异常");
            }
        }
        FilePlugin filePlugin = filePluginFactory.filePlugin();
        List<OssSliceUploadSignatureVO.SignatureInfo> signatureInfos = new ArrayList<>(chunkIndexList.size());
        for (Integer chunkIndex : chunkIndexList) {
            OSSSignature ossSignature = filePlugin.getOssSignature(3600,
                    getOssSliceUploadChunkObjectName(ossSliceUploadTaskInfo, chunkIndex));
            signatureInfos.add(OssSliceUploadSignatureVO.SignatureInfo.builder()
                    .index(chunkIndex)
                    .signature(ossSignature)
                    .build());
        }
        return OssSliceUploadSignatureVO.builder()
                .signatures(signatureInfos)
                .chunkSize(ossSliceUploadTaskInfo.getChunkSize())
                .totalChunk(ossSliceUploadTaskInfo.getTotalChunk())
                .hash(ossSliceUploadTaskInfo.getFileInfo().getHash())
                .build();
    }

    /**
     * 标记上传成功的id
     * @param uploadId 上传的id
     * @param chunkIndexList 完成的分片id列表
     * @return
     */
    @Override
    public OssSliceUploadChunkMarkVO markOssUploadSlice(String uploadId, Set<Integer> chunkIndexList) {
        Long userId = userId = 0L;
        String setKey = getOssSliceUploadTaskFinishedSliceIndexSetKey(uploadId);
        OssSliceUploadTaskInfo ossSliceUploadTaskInfo = redisService.getCacheObject(getOssSliceUploadTaskInfoKey(userId, uploadId));
        FilePlugin filePlugin = filePluginFactory.filePlugin();
        List<Integer> markedChunkIndexList = new ArrayList<>(chunkIndexList.size());
        try {
            for (Integer chunkIndex : chunkIndexList) {
                boolean isExist = filePlugin.exist(getOssSliceUploadChunkObjectName(ossSliceUploadTaskInfo, chunkIndex));
                if (!isExist) {
                    log.info(StringUtils.format("分片：{} 不存在", chunkIndex));
                }
                else {
                    markedChunkIndexList.add(chunkIndex);
                }
            }
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("检查分片错误");
        }

        redisService.addToSet(setKey, chunkIndexList);
        return OssSliceUploadChunkMarkVO.builder()
                .markedIndexList(markedChunkIndexList)
                .build();
    }


    @Override
    public OssSliceUploadComposeOV ossSliceUploadComposeObject(String uploadId) {
        Long userId = userId = 0L;
        OssSliceUploadTaskInfo ossSliceUploadTaskInfo = redisService
                .getCacheObject(getOssSliceUploadTaskInfoKey(userId, uploadId));
        FilePlugin filePlugin = filePluginFactory.filePlugin();
        List<String> objectNameList = IntStream
                .range(1, ossSliceUploadTaskInfo.getChunkSize()+1)
                .mapToObj(chunkIndex -> getOssSliceUploadChunkObjectName(ossSliceUploadTaskInfo, chunkIndex))
                .collect(Collectors.toList());
        OssObjectComposeResponse ossObjectComposeResponse = filePlugin
                .composeOssSliceUploadObject(objectNameList, ossSliceUploadTaskInfo.getObjectName());

        return OssSliceUploadComposeOV.builder()
                .objectName(ossObjectComposeResponse.getObjectName())
                .hash(ossObjectComposeResponse.getHash())
                .build();
    }
}
