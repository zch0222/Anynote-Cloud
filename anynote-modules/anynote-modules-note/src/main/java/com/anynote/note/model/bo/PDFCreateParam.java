package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
public class PDFCreateParam extends KnowledgeBaseQueryParam {

    private CommonsMultipartFile pdf;

    private String uploadId;


    @Builder(builderMethodName = "PDFCreateParamBuilder")
    public PDFCreateParam(Long knowledgeBaseId, CommonsMultipartFile pdf, String uploadId) {
        this.setId(knowledgeBaseId);
        this.pdf = pdf;
        this.uploadId = uploadId;
    }

    public Long getKnowledgeBaseId() {
        return this.getId();
    }

    public void setKnowledgeBaseId(Long id) {
        this.setId(id);
    }
}
