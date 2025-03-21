package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.MoocItemListVO;
import com.anynote.note.model.vo.MoocListVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 慕课服务
 * @author 称霸幼儿园
 */
public interface MoocService extends IService<MoocPO> {

    /**
     * 创建慕课
     * @param moocCreateParam 慕课创建参数
     * @return 慕课id
     */
    public Long createMooc(MoocCreateParam moocCreateParam);

    /**
     * 分页获取慕课列表
     * @param moocQueryParam
     * @return
     */
    public PageBean<MoocListVO> getMoocList(MoocQueryParam moocQueryParam);

    /**
     * 创建慕课封面上传任务
     * @param ossSliceUploadTaskCreatePublicDTO
     * @return
     */
    public OssSliceUploadTaskVO createMoocCoverUploadTask(OssSliceUploadTaskCreatePublicDTO ossSliceUploadTaskCreatePublicDTO);


    /**
     * 创建慕课Item
     * @param moocItemCreateParam 慕课Item
     * @return SUCCESS
     */
    public String createItems(MoocItemCreateParam moocItemCreateParam);

    /**
     *
     * @param moocItemQueryParam 查询param
     * @return moocItem列表
     */
    public PageBean<MoocItemListVO> getMoocItemList(MoocItemQueryParam moocItemQueryParam);

    /**
     * 创建慕课视频上传任务
     * @param moocVideoCreateParam 慕课视频上传任务创建Param
     * @return
     */
    public OssSliceUploadTaskVO createMoocVideoUploadTask(MoocVideoCreateParam moocVideoCreateParam);

    /**
     * 慕课对象语音识别
     * @param moocItemAsrParam
     * @return
     */
    public String moocItemAsr(MoocItemAsrParam moocItemAsrParam);
}