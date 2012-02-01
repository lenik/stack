package com.bee32.plover.faces.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;

public class ValidBeanTest
        extends Assert {

    public static void main(String[] args) {
        ValidBean bean = new ValidBean();
        bean.setUserName("a");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<ValidBean>> violations = validator.validate(bean);
        for (ConstraintViolation<ValidBean> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            System.err.println("invalid value for: '" + propertyPath + "': " + message);
        }
    }

}
