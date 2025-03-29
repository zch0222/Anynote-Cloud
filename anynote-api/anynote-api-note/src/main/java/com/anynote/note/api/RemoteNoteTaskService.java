package com.anynote.note.api;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.factory.RemoteNoteTaskFallbackFactory;
import com.anynote.note.api.model.po.UserNoteTask;
import com.anynote.note.api.model.vo.AdminNoteTaskVO;
import com.anynote.note.api.model.vo.NoteTaskChartsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(contextId = "remoteNoteTaskService", value =
        ServiceNameConstants.NOTE_SERVICE, fallbackFactory = RemoteNoteTaskFallbackFactory.class)
public interface RemoteNoteTaskService {

    @GetMapping("noteTasks/inner/taskUsers/{taskId}")
    public ResData<List<UserNoteTask>> getTaskUsers(@PathVariable("taskId") Long taskId,
                                                    @RequestHeader("from-source") String fromSource);

    @GetMapping("noteTasks/{id}/charts")
    public ResData<List<NoteTaskChartsVO>> getNoteTaskChartsData(@NotNull(message = "任务ID不能为空") @PathVariable("id") Long id,
                                                                 @RequestHeader("from-source") String fromSource,
                                                                 @RequestHeader("accessToken") String accessToken);

    @GetMapping("/admin/noteTasks/{id}")
    public ResData<AdminNoteTaskVO> getAdminNoteTaskById(@PathVariable("id") @NotNull(message = "任务id不能为空") Long id,
                                                         @RequestHeader("from-source") String fromSource,
                                                         @RequestHeader("accessToken") String accessToken);
}
