package com.anynote.note.validate.aspect;

import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.note.model.bo.KnowledgeBaseQueryParam;
import com.anynote.note.model.bo.NoteQueryParam;
import com.anynote.note.validate.annotation.PageValid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 称霸幼儿园
 */
@Component
@Aspect
@Order(-1)
public class PageValidAspect {


    @Before("@annotation(pageValid)")
    public void valid(JoinPoint joinPoint, PageValid pageValid) {
        if (joinPoint.getArgs()[0] instanceof KnowledgeBaseQueryParam) {
            KnowledgeBaseQueryParam queryParam = (KnowledgeBaseQueryParam) joinPoint.getArgs()[0];
            valid(queryParam.getPage(), queryParam.getPageSize());
        }
        else if (joinPoint.getArgs()[0] instanceof NoteQueryParam) {
            NoteQueryParam queryParam = (NoteQueryParam) joinPoint.getArgs()[0];
            valid(queryParam.getPage(), queryParam.getPageSize());
        }
        else {
            throw new RuntimeException("请传入正确的分页参数");
        }
    }

    private void valid(Integer page, Integer pageSize) {
        if (StringUtils.isNull(page)) {
            throw new UserParamException("页码不能为空", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        if (StringUtils.isNull(pageSize)) {
            throw new UserParamException("页面大小不能为空", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        if (page < 1) {
            throw new UserParamException("页码错误", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        if (pageSize < 1) {
            throw new UserParamException("页面大小错误", ResCode.USER_REQUEST_PARAM_ERROR);
        }
    }
}
