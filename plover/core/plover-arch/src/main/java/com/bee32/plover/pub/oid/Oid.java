package com.bee32.plover.pub.oid;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * OID 编号
 *
 * 其中 {@link #owner()} 默认为 BEE32，如果是外部提供的应用，应该填写正确的属主，或者在 BEE32 下另行分配。
 *
 * @see <a href="http://track.secca-project.com/projects/sandbox/wiki/oid-37042">Bee32 OID 分配表</a>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Oid {

    /**
     * OID 属主。
     *
     * 默认为 bee32.com：1.3.6.1.4.1.37042
     */
    int[] owner() default { 1, 3, 6, 1, 4, 1, 37042 };

    int[] value();

}
