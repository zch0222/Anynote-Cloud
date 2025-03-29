package com.anynote.file.api.factory;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.model.bo.*;
import com.anynote.file.api.model.dto.CompleteUploadDTO;
import com.anynote.file.api.model.dto.CreateHuaweiOBSTemporarySignatureDTO;
import com.anynote.file.api.model.dto.DownloadObjectDTO;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreateDTO;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务降级
 * @author 称霸幼儿园
 */
@Slf4j
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {


    @Override
    public RemoteFileService create(Throwable cause) {
        log.error("文件服务调用失败: {}", cause.getMessage());
        return new RemoteFileService() {

            @Override
            public ResData<FilePO> uploadFile(MultipartFile file, String path, Long userId, String uploadId, Integer source) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }

            @Override
            public ResData<UploadProgress> getFileUploadProgress(String uploadId) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }

            @Override
            public ResData<HuaweiOBSTemporarySignature> createHuaweiOBSTemporarySignature(CreateHuaweiOBSTemporarySignatureDTO createHuaweiOBSTemporarySignatureDTO) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }

            @Override
            public ResData<FilePO> completeHuaweiOBSUpload(CompleteUploadDTO completeUploadDTO) {
                throw new BusinessException(ResCode.INNER_FILE_SERVICE_ERROR);
            }

            @Override
            public ResData<FilePO> getFileById(Long id, String fromSource) {
                throw new BusinessException("获取文件失败");
            }

            @Override
            public ResData<OssSliceUploadTaskVO> createOssSliceUploadTask(OssSliceUploadTaskCreateDTO ossSliceUploadTaskCreateDTO) {
                throw new BusinessException("创建分片任务失败");
            }

            @Override
            public ResData<String> downloadObject(DownloadObjectDTO downloadObjectDTO) {
                throw new BusinessException(StringUtils.format("下载文件对象\"{}\"失败",
                        downloadObjectDTO.getObjectName()));
            }

            @Override
            public ResData<String> downloadObject(DownloadObjectDTO downloadObjectDTO, String fromSource) {
                throw new BusinessException(StringUtils.format("下载文件对象\"{}\"失败",
                        downloadObjectDTO.getObjectName()));
            }


            @Override
            public ResData<String> readTextFile(String objectName, String fromSource) {
                throw new BusinessException("调用readTextFile失败");
            }
        };
    }
}
