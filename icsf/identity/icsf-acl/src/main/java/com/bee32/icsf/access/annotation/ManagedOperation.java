package com.bee32.icsf.access.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bee32.icsf.access.IManagedOperation;
import com.bee32.icsf.access.Permission;

/**
 * 注解受控方法
 *
 * 你可以通过 Java 5 注解来声明受控方法，并通过资源属性来实现 I18n。
 *
 * 例如你有如下类，
 *
 * <pre>
 * class MyBusiness {
 *
 *     &#064;ManagedOperation(name = &quot;method1&quot;, require = MyPermission.class)
 *     void myMethod() {
 *         // ...
 *     }
 *
 * }
 *</pre>
 *
 * 那么，对应的属性资源为 MyBusiness.properties，该属性中下列的键值被用于说明的目的：
 * <ul>
 * <li><code>method1_displayName</code> = 显示名称
 * <li><code>method1_description</code> = 描述信息
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
public @interface ManagedOperation {

    /**
     * 默认通过 Java Reflect 使用作用类或方法的名称。
     */
    String name() default "";

    /**
     * 默认使用名称为“操作的名称+Permission”的静态属性（无继承），如果不存在这样的属性则不需要权限。
     * <p>
     * 在 {@link AnnotatedAccessControlInfo} 实现中， {@link #require()} 声明的权限可以被
     * {@link AnnotatedAccessControlInfo#getOverridedRequiredPermission(String)} 所覆盖。
     *
     * 对于或出于性能原因、或存在动态的权限特性，需要由运行时决定所需的权限时，通过重写
     * {@link AnnotatedAccessControlInfo#getOverridedRequiredPermission(String)} 是非常有用的。
     *
     * @see AnnotatedAccessControlInfo#getOverridedRequiredPermission(String)
     * @return 声明访问本操作所需的最低权限。
     */
    Class<? extends Permission> require() default DefaultPermission.class;

    int REFLECTED = -1;

    /**
     * 默认通过 Java Reflect 取得可见性：
     * <table border="1">
     * <tr>
     * <th>Java Accessible</th>
     * <th>Operation Visibility</th>
     * </tr>
     * <tr>
     * <td>public</td>
     * <td>{@link IManagedOperation#PUBLIC}</td>
     * </tr>
     * <tr>
     * <td>protected</td>
     * <td>{@link IManagedOperation#MODULE}</td>
     * </tr>
     * <tr>
     * <td>package</td>
     * <td>{@link IManagedOperation#PRIVATE}</td>
     * </tr>
     * <tr>
     * <td>private</td>
     * <td>{@link IManagedOperation#PRIVATE}</td>
     * </tr>
     * </table>
     */
    int visibility() default REFLECTED;

}
