package com.anynote.note.model.vo;

import com.anynote.core.web.model.bo.BaseEntity;
import io.swagger.models.auth.In;
import lombok.*;

import java.util.Date;

/**
 * 慕课VO
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocListVO extends BaseEntity {

    /**
     * 慕课id
     */
    private Long id;

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 慕课描述
     */
    private String moocDescription;

    /**
     * 数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见
     */
    private Integer dataScope;

    /**
     * 所属知识库id 0表示不属于任何知识库
     */
    private Long knowledgeBaseId;

    /**
     * 慕课权限
     */
    private Integer moocPermission;

    /**
     * 慕课创建者用户名
     */
    private String creatorUsername;

    /**
     * 用户慕课权限
     */
    private Integer userPermission;

    @Builder
    public MoocListVO(Long id, String title, String moocDescription,
                      Integer dataScope, Long knowledgeBaseId, Integer moocPermission,
                      Long createBy, Date createTime, Long updateBy, Date updateTime) {
        this.id = id;
        this.title = title;
        this.moocDescription = moocDescription;
        this.dataScope = dataScope;
        this.knowledgeBaseId = knowledgeBaseId;
        this.moocPermission = moocPermission;
        setCreateBy(createBy);
        setCreateTime(createTime);
        setUpdateBy(updateBy);
        setUpdateTime(updateTime);
    }
}
