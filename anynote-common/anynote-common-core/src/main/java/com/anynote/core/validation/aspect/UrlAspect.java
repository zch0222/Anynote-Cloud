package com.anynote.core.validation.aspect;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.properties.ExternalResourcesProperties;
import com.anynote.core.validation.annotation.Url;
import com.anynote.core.web.enums.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

@Aspect
@Component
@Slf4j
public class UrlAspect {

    @Resource
    private ExternalResourcesProperties externalResourcesProperties;


    @Before("@annotation(url)")
    public void doBefore(JoinPoint joinPoint, Url url) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] argNames = signature.getParameterNames();

        for (int i = 0; i < argNames.length; ++i) {
            if (url.param().equals(argNames[i])) {
                // 首字母大写
                String capitalized = url.value().substring(0, 1).toUpperCase() +
                        url.value().substring(1);
                String getterName = "get" + capitalized;
                try {
                    Method method = args[i].getClass().getMethod(getterName);
                    URL link = new URL((String) method.invoke(args[i]));
                    String domain = link.getHost();
                    validate(domain);
                } catch (NoSuchMethodException | IllegalAccessException | MalformedURLException e) {
                    log.error(e.getMessage());
                    throw new BusinessException(ResCode.BUSINESS_ERROR);
                } catch (InvocationTargetException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    private void validate(String domain) {
        for (String allowDomain : externalResourcesProperties.getAllowedDomains()) {
            if (allowDomain.equals(domain)) {
                return;
            }
        }
        throw new BusinessException(ResCode.RESOURCE_DOMAINS_NOT_ALLOWED);
    }
}
