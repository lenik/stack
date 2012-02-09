package com.bee32.plover.faces.view;

import java.util.Set;

import javax.validation.ConstraintViolation;

public interface IValidatable {

    Set<ConstraintViolation<?>> validate();

}
