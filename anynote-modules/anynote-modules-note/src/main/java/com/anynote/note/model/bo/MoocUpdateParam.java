package com.anynote.note.model.bo;

import com.anynote.note.model.dto.MoocUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocUpdateParam extends MoocParam {

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

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
     * 权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)
     */
    private String moocPermissions;

    public MoocUpdateParam(long moocId, MoocUpdateDTO moocUpdateDTO) {
        setMoocId(moocId);
        setTitle(moocUpdateDTO.getTitle());
        setCover(moocUpdateDTO.getCover());
        setMoocDescription(moocUpdateDTO.getMoocDescription());
        setDataScope(moocUpdateDTO.getDataScope());
        setKnowledgeBaseId(moocUpdateDTO.getKnowledgeBaseId());
        this.moocPermissions = moocUpdateDTO.getPermissions();
    }

}
