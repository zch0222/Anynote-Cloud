package com.anynote.core.utils;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.ResData;

public class RemoteResDataUtil {

    public static <T> T getResData(ResData<T> resData, String errorMessage) {
        if (StringUtils.isNull(resData) || StringUtils.isNull(resData.getData())) {
            throw new BusinessException(errorMessage);
        }

        if (!ResData.SUCCESS.equals(resData.getCode())) {
            throw new BusinessException(errorMessage);
        }
        return resData.getData();
    }
}
