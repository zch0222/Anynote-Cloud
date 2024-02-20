package com.anynote.note.model.dto;

import com.anynote.file.api.model.dto.ImageUploadTempLinkDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class KnowledgeBaseCoverUploadTempLinkDTO extends ImageUploadTempLinkDTO {

}
