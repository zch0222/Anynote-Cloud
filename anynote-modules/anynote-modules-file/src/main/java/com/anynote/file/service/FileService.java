package com.anynote.file.service;

import com.anynote.file.api.model.bo.HuaweiOBSTemporarySignature;
import com.anynote.file.api.model.bo.UploadProgress;
import com.anynote.file.api.model.dto.CompleteUploadDTO;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.file.api.model.vo.OssSliceUploadChunkMarkVO;
import com.anynote.file.api.model.vo.OssSliceUploadComposeOV;
import com.anynote.file.api.model.vo.OssSliceUploadSignatureVO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Set;

/**
 * 文件上传
 * @author 称霸幼儿园
 */
public interface FileService extends IService<FilePO> {


    public FilePO upload(CommonsMultipartFile file, String path, Long userId, String uploadId, Integer source);


    public UploadProgress getFileUploadProgress(String uploadId);

    public HuaweiOBSTemporarySignature createHuaweiOBSTemporarySignature(String path, String fileName,
                                                                         Long expireSeconds, String contentType,
                                                                         Integer source);

    /**
     * 完成上传回调
     * @param completeUploadDTO 完成上传DTO
     * @return file ID
     */
    public FilePO completeUpload(CompleteUploadDTO completeUploadDTO);

    public FilePO getFileById(Long id);

    public OssSliceUploadTaskVO createOssSliceUploadTask(String path, String fileName,
                                                         String hash, Double fileSize, String contentType,
                                                         Integer source);

    /**
     * 获取上传分片的签名
     * @param uploadId 文件上传id
     * @param chunkIndexList 分片id列表
     * @return 分片签名
     */
    public OssSliceUploadSignatureVO getOssSliceUploadSignature(String uploadId, List<Integer> chunkIndexList);


    /**
     * 标记上传完成的分片
     * @param uploadId 上传的id
     * @param chunkIndexList 完成的分片id列表
     * @return
     */
    public OssSliceUploadChunkMarkVO markOssUploadSlice(String uploadId, Set<Integer> chunkIndexList);


    /**
     * 对象存储合并上传的分片
     * @param uploadId 文件上传id
     * @return
     */
    public OssSliceUploadComposeOV ossSliceUploadComposeObject(String uploadId);

}
