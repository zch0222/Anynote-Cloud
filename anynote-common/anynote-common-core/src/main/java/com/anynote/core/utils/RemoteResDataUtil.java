package com.anynote.core.utils;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.ResData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteResDataUtil {

    public static <T> T getResData(ResData<T> resData, String errorMessage) {
        if (StringUtils.isNull(resData) || StringUtils.isNull(resData.getData())) {
            throw new BusinessException(errorMessage);
        }

        if (!ResData.SUCCESS.equals(resData.getCode())) {
            log.error(resData.getMsg());
            throw new BusinessException(errorMessage);
        }
        return resData.getData();
    }

    public static <T> T getResData(ResData<T> resData) {
        if (StringUtils.isNull(resData) || StringUtils.isNull(resData.getData())) {
            throw new BusinessException("未知异常");
        }

        if (!ResData.SUCCESS.equals(resData.getCode())) {
            log.error(resData.getMsg());
            throw new BusinessException(resData.getMsg());
        }
        return resData.getData();
    }
}
