package com.anynote.note.model.dto;

import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocVideoUploadTaskCreateDTO extends OssSliceUploadTaskCreatePublicDTO {

    @NotNull(message = "慕课ID不能为空")
    private Long moocId;
}
