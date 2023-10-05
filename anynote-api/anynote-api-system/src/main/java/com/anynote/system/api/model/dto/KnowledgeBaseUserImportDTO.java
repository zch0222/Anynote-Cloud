package com.anynote.system.api.model.dto;

import com.anynote.system.api.model.bo.KnowledgeBaseImportUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBaseUserImportDTO {
    @NotNull(message = "知识库用户列表不能为空")
    private List<KnowledgeBaseImportUser> knowledgeBaseImportUserList;

    private List<String> failUserNameList;

    /**
     * 失败条数
     */
    private Integer failCount;
}
