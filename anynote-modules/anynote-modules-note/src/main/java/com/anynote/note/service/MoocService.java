package com.anynote.note.service;

import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.common.elasticsearch.model.bo.EsMoocIndex;
import com.anynote.common.elasticsearch.model.bo.SearchPageBean;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.anynote.note.api.model.dto.MoocSearchDTO;
import com.anynote.note.api.model.vo.MoocVideoItemInfoVO;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 慕课服务
 * @author 称霸幼儿园
 */
public interface MoocService extends IService<MoocPO> {

    /**
     * 根据慕课id获取慕课信息
     * @param moocQueryParam 慕课查询参数
     * @return 慕课信息
     */
    public MoocVO getMoocById(MoocQueryParam moocQueryParam);

    /**
     * 创建慕课
     * @param moocCreateParam 慕课创建参数
     * @return 慕课id
     */
    public Long createMooc(MoocCreateParam moocCreateParam);

    /**
     * 更新慕课
     * @param moocUpdateParam 慕课更新参数
     * @return
     */
    public String updateMooc(MoocUpdateParam moocUpdateParam);

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
     * 创建一个慕课Item
     * @param moocItemCreateParam
     * @return 新建的慕课Item id
     */
    public Long createSingleItem(MoocItemCreateParam moocItemCreateParam);


    /**
     * 更新Mooc Item
     * @param moocItemUpdateParam MoocItem更新参数
     * @return SUCCESS
     */
    public String updateMoocItem(MoocItemUpdateParam moocItemUpdateParam);

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
    public MoocItemAsrVO moocItemAsr(MoocItemAsrParam moocItemAsrParam);

    public String updateAsrInfo(MoocAsrInfoUpdateDTO moocAsrInfoUpdateDTO);

    public MoocAsrTaskInfo getMoocAsrTaskInfo(MoocItemQueryParam moocItemQueryParam);

    /**
     * 获取视频慕课信息
     * @param moocItemQueryParam
     * @return
     */
    public MoocVideoItemInfoVO getMoocVideoItemInfo(MoocItemQueryParam moocItemQueryParam);

    public String deleteMooc(MoocParam moocParam);

    /**
     * 批量删除慕课Items
     * @param moocItemBatchDeleteItemParam
     * @return
     */
    public String batchDeleteMoocItems(MoocItemBatchDeleteItemParam moocItemBatchDeleteItemParam);

    /**
     * 搜索慕课信息
     * @param moocSearchDTO
     * @return
     */
    public SearchPageBean<EsMoocIndex> searchMooc(MoocSearchDTO moocSearchDTO);


    /**
     * 获取用户可见的慕课id列表
     * @return 用户可见的慕课id列表
     */
    public List<Long> getVisibleMoocIds(Long userId);

    /**
     * 获取慕课视频AI总结
     * @param moocItemQueryParam
     * @return
     */
    public List<MoocVideoSummarizePO> getMoocVideoSummarize(MoocItemQueryParam moocItemQueryParam);

}