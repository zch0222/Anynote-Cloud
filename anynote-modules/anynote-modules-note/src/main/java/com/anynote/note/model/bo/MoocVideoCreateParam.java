package com.anynote.note.model.bo;

import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocVideoCreateParam extends MoocParam {

    private OssSliceUploadTaskCreatePublicDTO ossSliceUploadTaskCreatePublicDTO;

    @Builder(builderMethodName = "MoocVideoCreateParamBuilder")
    public MoocVideoCreateParam(OssSliceUploadTaskCreatePublicDTO ossSliceUploadTaskCreatePublicDTO, Long moocId) {
        this.ossSliceUploadTaskCreatePublicDTO = ossSliceUploadTaskCreatePublicDTO;
        super.setMoocId(moocId);
    }
}
