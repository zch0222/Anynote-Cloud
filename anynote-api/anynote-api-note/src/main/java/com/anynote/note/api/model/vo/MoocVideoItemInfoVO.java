package com.anynote.note.api.model.vo;

import lombok.*;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoocVideoItemInfoVO {

    /**
     * 慕课视频信息id
     */
    private Long id;

    /**
     * 慕课id
     */
    private Long moocId;

    /**
     * 慕课
     */
    private Long moocItemId;

    /**
     * 字幕对象名称
     */
    private String srtObjectName;

    /**
     * 总结
     */
    private String videoSummarize;


}
