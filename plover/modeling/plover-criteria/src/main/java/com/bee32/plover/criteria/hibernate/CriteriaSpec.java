package com.bee32.plover.criteria.hibernate;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;

/**
 * CriteriaTemplate 的子类中定义查询用的谓词。
 *
 * <p>
 * 谓词命名的一般原则：<br>
 * 对于 {@link LeftHand} 中声明的类 C=Foo 和谓词 P=pred，应该可以读作： <cite> Foo is pred.</cite>
 *
 * <p>
 * 例如，一个用于判断 Person 是否为成年人 （年龄 >= 18）的谓词 mature：
 *
 * <pre>
 * &#64;{@link LeftHand}(Person.class) Criterion matured();
 * </pre>
 *
 * 可以读作 <cite> Person is matured. </cite>
 */
public abstract class CriteriaSpec {

    protected static CriteriaComposite compose(CriteriaElement... elements) {
        return new CriteriaComposite(elements);
    }

    protected static CriteriaElement alias(String associationPath, String alias) {
        return new Alias(associationPath, alias);
    }

    protected static CriteriaElement alias(String associationPath, String alias, int joinType) {
        return new Alias(associationPath, alias, joinType);
    }

    protected static ICriteriaElement ascOrder(String propertyName) {
        return Order.asc(propertyName);
    }

    protected static ICriteriaElement descOrder(String propertyName) {
        return Order.desc(propertyName);
    }

    protected static CriteriaElement idEq(Object value) {
        return new IdEquals(value);
    }

    protected static CriteriaElement equals(String propertyName, Object value) {
        return new Equals(propertyName, value);
    }

    protected static CriteriaElement notEquals(String propertyName, Object value) {
        return new NotEquals(propertyName, value);
    }

    protected static CriteriaElement like(String propertyName, String value) {
        return new Like(propertyName, value);
    }

    protected static CriteriaElement like(String propertyName, String value, MatchMode matchMode) {
        return new Like(propertyName, value, matchMode);
    }

    protected static CriteriaElement likeIgnoreCase(String propertyName, String value) {
        return new LikeIgnoreCase(propertyName, value);
    }

    protected static CriteriaElement likeIgnoreCase(String propertyName, String value, MatchMode matchMode) {
        return new LikeIgnoreCase(propertyName, value, matchMode);
    }

    protected static CriteriaElement greaterThan(String propertyName, Object value) {
        return new GreaterThan(propertyName, value);
    }

    protected static CriteriaElement lessThan(String propertyName, Object value) {
        return new LessThan(propertyName, value);
    }

    protected static CriteriaElement lessOrEquals(String propertyName, Object value) {
        return new LessOrEquals(propertyName, value);
    }

    protected static CriteriaElement greaterOrEquals(String propertyName, Object value) {
        return new GreaterOrEquals(propertyName, value);
    }

    protected static CriteriaElement between(String propertyName, Object lo, Object hi) {
        return new Between(propertyName, lo, hi);
    }

    protected static CriteriaElement in(String propertyName, Object... values) {
        return new InArray(propertyName, values);
    }

    protected static CriteriaElement in(String propertyName, Collection<?> values) {
        return new InCollection(propertyName, values);
    }

    protected static CriteriaElement isNull(String propertyName) {
        return new IsNull(propertyName);
    }

    protected static CriteriaElement propertyEquals(String propertyName, String otherPropertyName) {
        return new PropertyEquals(propertyName, otherPropertyName);
    }

    protected static CriteriaElement propertyNotEquals(String propertyName, String otherPropertyName) {
        return new PropertyNotEquals(propertyName, otherPropertyName);
    }

    protected static CriteriaElement propertyLessThan(String propertyName, String otherPropertyName) {
        return new PropertyLessThan(propertyName, otherPropertyName);
    }

    protected static CriteriaElement propertyLessOrEquals(String propertyName, String otherPropertyName) {
        return new PropertyLessOrEquals(propertyName, otherPropertyName);
    }

    protected static CriteriaElement propertyGreaterThan(String propertyName, String otherPropertyName) {
        return new PropertyGreaterThan(propertyName, otherPropertyName);
    }

    protected static CriteriaElement propertyGreaterOrEquals(String propertyName, String otherPropertyName) {
        return new PropertyGreaterOrEquals(propertyName, otherPropertyName);
    }

    protected static CriteriaElement isNotNull(String propertyName) {
        return new IsNotNull(propertyName);
    }

    protected static CriteriaElement sqlRestriction(String sql, Object[] values, Type[] types) {
        return new SqlRestriction(sql, values, types);
    }

    protected static CriteriaElement sqlRestriction(String sql, Object value, Type type) {
        return new SqlRestriction(sql, new Object[] { value }, new Type[] { type });
    }

    protected static CriteriaElement sqlRestriction(String sql) {
        return new SimpleSqlRestriction(sql);
    }

    protected static CriteriaElement allEq(Map<?, ?> propertyNameValues) {
        return new AllEquals(propertyNameValues);
    }

    protected static CriteriaElement isEmpty(String propertyName) {
        return new IsEmpty(propertyName);
    }

    protected static CriteriaElement isNotEmpty(String propertyName) {
        return new IsNotEmpty(propertyName);
    }

    protected static CriteriaElement sizeEq(String propertyName, int size) {
        return new SizeEquals(propertyName, size);
    }

    protected static CriteriaElement sizeNe(String propertyName, int size) {
        return new SizeNotEquals(propertyName, size);
    }

    protected static CriteriaElement sizeGt(String propertyName, int size) {
        return new SizeGreaterThan(propertyName, size);
    }

    protected static CriteriaElement sizeLt(String propertyName, int size) {
        return new SizeLessThan(propertyName, size);
    }

    protected static CriteriaElement sizeGe(String propertyName, int size) {
        return new SizeGreaterOrEquals(propertyName, size);
    }

    protected static CriteriaElement sizeLe(String propertyName, int size) {
        return new SizeLessOrEquals(propertyName, size);
    }

    protected static CriteriaElement naturalId() {
        return new NaturalId();
    }

    protected static Conjunction conjunction() {
        return new Conjunction();
    }

    protected static Disjunction disjunction() {
        return new Disjunction();
    }

    protected static CriteriaElement and(CriteriaElement lhs, CriteriaElement rhs) {
        return new And(lhs, rhs);
    }

    protected static CriteriaElement or(CriteriaElement lhs, CriteriaElement rhs) {
        return new Or(lhs, rhs);
    }

    protected static CriteriaElement not(CriteriaElement expression) {
        return new Not(expression);
    }

    // Projections.
    {
        Projections.projectionList();
    }
}
