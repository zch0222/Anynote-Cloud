package com.anynote.core.context;

import com.anynote.core.web.enums.HttpStatusEnum;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 称霸幼儿园
 */
public class HttpContextHolder {

    public static void setStatus(HttpStatusEnum status) {
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(status.getValue());
    }
}
