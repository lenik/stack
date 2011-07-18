package com.bee32.plover.criteria.hibernate;

/**
 * CriteriaTemplate 的子类中定义查询用的谓词。
 *
 * <p>
 * 谓词命名的一般原则：<br>
 * 对于 {@link QueryEntity} 中声明的类 C=Foo 和谓词 P=pred，应该可以读作： <cite> Foo is pred.</cite>
 *
 * <p>
 * 例如，一个用于判断 Person 是否为成年人 （年龄 >= 18）的谓词 mature：
 *
 * <pre>
 * &#64;{@link QueryEntity}(Person.class) Criterion matured();
 * </pre>
 *
 * 可以读作 <cite> Person is matured. </cite>
 */
public abstract class CriteriaTemplate {

}
