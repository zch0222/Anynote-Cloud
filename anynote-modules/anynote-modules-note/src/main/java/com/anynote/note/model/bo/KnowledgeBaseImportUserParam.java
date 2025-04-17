package com.anynote.note.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KnowledgeBaseImportUserParam extends KnowledgeBaseQueryParam{

    private MultipartFile excel;
}
