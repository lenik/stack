package com.bee32.plover.restful.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import com.bee32.plover.restful.util.IRestfulController;

@Inherited
@Retention(RUNTIME)
@Documented
public @interface UseController {

    Class<? extends IRestfulController> value();

}
