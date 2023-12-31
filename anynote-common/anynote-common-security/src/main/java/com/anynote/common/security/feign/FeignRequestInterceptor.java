package com.anynote.common.security.feign;

import com.anynote.core.utils.ServletUtils;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Map;

/**
 * feign 请求拦截器
 *
 * @author 称霸幼儿园
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest))
        {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String userId = headers.get(SecurityConstants.DETAILS_USER_ID);
            if (StringUtils.isNotEmpty(userId))
            {
                requestTemplate.header(SecurityConstants.DETAILS_USER_ID, userId);
            }
            String userKey = headers.get(SecurityConstants.USER_KEY);
            if (StringUtils.isNotEmpty(userKey))
            {
                requestTemplate.header(SecurityConstants.USER_KEY, userKey);
            }
            String userName = headers.get(SecurityConstants.DETAILS_USERNAME);
            if (StringUtils.isNotEmpty(userName))
            {
                requestTemplate.header(SecurityConstants.DETAILS_USERNAME, userName);
            }
            String authentication = headers.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication))
            {
                requestTemplate.header(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }
            String accessToken = headers.get(SecurityConstants.ACCESS_TOKEN);
            if (StringUtils.isNotEmpty(accessToken)) {
                requestTemplate.header(SecurityConstants.ACCESS_TOKEN, accessToken);
            }

//            String contentType = headers.get(SecurityConstants.CONTENT_TYPE);
//            if (StringUtils.isNotEmpty(contentType)) {
//                requestTemplate.header(SecurityConstants.CONTENT_TYPE, contentType);
//            }

        }
    }
}
