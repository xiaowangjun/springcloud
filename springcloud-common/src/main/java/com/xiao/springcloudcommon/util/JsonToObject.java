package com.xiao.springcloudcommon.util;

import com.xiao.springcloudcommon.exception.CustomBizException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class JsonToObject {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> T checkAndParseParams(String json, Class<T> clazz) {
        T t = JSONUtil.fromJson(json, clazz);
        checkParams(t);
        return t;
    }


    public static void checkParams(Object object) {
        StringBuilder errorBuilder = new StringBuilder();
        Set violations = validator.validate(object);
        for (Object violation : violations) {
            ConstraintViolation constraintViolation = (ConstraintViolation) violation;
            if (StringUtils.isNotBlank(errorBuilder)) {
                errorBuilder.append(" ");
            }
            errorBuilder.append(constraintViolation.getMessage());
        }
        if (StringUtils.isNotBlank(errorBuilder.toString())) {
            throw new CustomBizException(ApiCodeEnum.PARAM_ERROR, errorBuilder.toString());
        }

    }
}
