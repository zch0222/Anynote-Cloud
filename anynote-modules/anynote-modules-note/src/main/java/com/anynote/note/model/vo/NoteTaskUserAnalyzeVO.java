package com.anynote.note.model.vo;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.model.po.NoteTaskAnalyzePO;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoteTaskUserAnalyzeVO extends PageBean<NoteTaskAnalyzePO> {

    /**
     * 平均编辑次数
     */
    private Double averageEditCounts;

    @Builder(builderMethodName = "NoteTaskUserAnalyzeVOBuilder")
    public NoteTaskUserAnalyzeVO(List<NoteTaskAnalyzePO> rows, Long total, Integer current, Integer pages,
                                 Double averageEditCounts) {
        this.averageEditCounts = averageEditCounts;
        this.setRows(rows);
        this.setTotal(total);
        this.setCurrent(current);
        this.setPages(pages);
    }

}
