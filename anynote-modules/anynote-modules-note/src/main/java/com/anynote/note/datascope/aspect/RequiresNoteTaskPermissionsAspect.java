package com.anynote.note.datascope.aspect;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.exception.auth.AuthException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.note.datascope.annotation.RequiresNoteTaskPermissions;
import com.anynote.note.enums.NoteTaskPermissions;
import com.anynote.note.mapper.UserNoteTaskMapper;
import com.anynote.note.model.bo.NoteQueryParam;
import com.anynote.note.model.bo.NoteTaskQueryParam;
import com.anynote.note.model.bo.NoteTaskUpdateParam;
import com.anynote.note.service.NoteTaskService;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
@Aspect
@Component
@Order(3)
public class RequiresNoteTaskPermissionsAspect {

    @Resource
    private TokenUtil tokenUtil;


    @Resource
    private NoteTaskService noteTaskService;

    public static final String NOTE_TASK_PERMISSIONS = "noteTaskPermissions";

    @Before("@annotation(requiresNoteTaskPermissions)")
    public void doBefore(JoinPoint joinPoint, RequiresNoteTaskPermissions requiresNoteTaskPermissions) {
        NoteTaskQueryParam noteTaskQueryParam = getParam(joinPoint);
        if (StringUtils.isNull(noteTaskQueryParam)) {
            throw new AuthException(requiresNoteTaskPermissions.message(), ResCode.UNAUTHORIZED_ERROR);
        }
        authPermissions(noteTaskQueryParam, requiresNoteTaskPermissions);
    }

    private void authPermissions(NoteTaskQueryParam queryParam, RequiresNoteTaskPermissions requiresNoteTaskPermissions) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        if (SysUser.isAdminX(loginUser.getSysUser().getRole())) {
            return;
        }

        NoteTaskPermissions noteTaskPermissions = noteTaskService.getNoteTaskPermissions(loginUser.getSysUser().getId(),
                queryParam.getNoteTaskId());
         if (noteTaskPermissions.getValue() >  requiresNoteTaskPermissions.value().getValue()) {
             throw new AuthException(requiresNoteTaskPermissions.message(), ResCode.UNAUTHORIZED_ERROR);
         }
         addNoteTaskPermissions(queryParam, noteTaskPermissions);
    }

    private void addNoteTaskPermissions(NoteTaskQueryParam queryParam, NoteTaskPermissions noteTaskPermissions) {
        if (StringUtils.isNull(queryParam.getParams())) {
            Map<String, Object> map = new HashMap<>();
            map.put(NOTE_TASK_PERMISSIONS, noteTaskPermissions);
            queryParam.setParams(map);
        }
        else {
            queryParam.getParams().put(NOTE_TASK_PERMISSIONS, noteTaskPermissions);
        }
    }

    private NoteTaskQueryParam getParam(JoinPoint joinPoint) {
        Object param = joinPoint.getArgs()[0];
        if (StringUtils.isNull(param) || !(param instanceof NoteTaskQueryParam)) {
            return null;
        }
        return (NoteTaskQueryParam) param;
    }

}
