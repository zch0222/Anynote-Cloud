package com.anynote.ai.nio.model.bo;

import com.anynote.note.api.model.vo.AdminNoteTaskVO;
import com.anynote.note.api.model.vo.NoteTaskChartsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskSubmissionAnalyzeBO {

    private List<NoteTaskChartsVO> noteTaskChartsVOList;

    private AdminNoteTaskVO adminNoteTaskVO;
}
