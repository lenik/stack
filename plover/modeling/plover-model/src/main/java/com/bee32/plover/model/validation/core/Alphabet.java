package com.bee32.plover.model.validation.core;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bee32.plover.model.validation.core.impl.AlphabetValidator;

/**
 * NLength &#64;NLength 和 &#64;Length/Size 不同在于:
 *
 * <ul>
 * <li>&#64;NLength 先对字符串进行规范化，清除两边的空格，再计算长度。
 * <li>&#64;NLength 只能用于字符串，而 &#64;Size 可用于字符串、列表、集合等类型。
 * </ul>
 *
 * @see javax.validation.constraints.Size
 * @see com.bee32.plover.model.validation.legacy.Length
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphabetValidator.class)
public @interface Alphabet {

    String message() default "{com.bee32.plover.model.validation.Alphabet}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tab();

    String hint();

    String LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String LETTER_HINT = "字母";

    String ALNUM = LETTER + "0123456789";
    String ALNUM_HINT = "字母或数字";

}
