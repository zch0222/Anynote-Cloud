package com.anynote.note.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 称霸幼儿园
 */
@Data
public class KnowledgeBaseImportUserParam extends KnowledgeBaseQueryParam{

    private MultipartFile excel;
}
