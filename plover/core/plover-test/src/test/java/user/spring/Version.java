package user.spring;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

@Retention(RUNTIME)
@Documented
@Qualifier
public @interface Version {

    String value();

}
