package com.anynote.note.model.dto;

import com.anynote.system.api.model.dto.ImportFailUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 导入知识库用户返回类
 * @author 称霸幼儿园
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class KnowledgeBaseImportUserVO {

    /**
     * 名单链接
     */
    private String excelUrl;

//    private List<String> failUserNameList;
    private List<ImportFailUser> failUserList;

    /**
     * 失败条数
     */
    private Integer failCount;
}
