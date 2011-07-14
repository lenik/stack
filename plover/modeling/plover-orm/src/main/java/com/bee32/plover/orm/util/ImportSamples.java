package com.bee32.plover.orm.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RUNTIME)
@Target({ TYPE })
public @interface ImportSamples {

    Class<? extends SampleContribution>[] value();

}
