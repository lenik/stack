package com.bee32.plover.criteria.hibernate;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
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

    protected static CriteriaComposite compose(ICriteriaElement... elements) {
        return new CriteriaComposite(elements);
    }

    protected static ICriteriaElement limit(int limit) {
        return limit(0, limit);
    }

    protected static ICriteriaElement limit(int offset, int limit) {
        return new Limit(offset, limit);
    }

    protected static CriteriaElement alias(String associationPath, String alias) {
        return new Alias(associationPath, alias);
    }

    /**
     * @see CriteriaSpecification#INNER_JOIN
     * @see CriteriaSpecification#FULL_JOIN
     * @see CriteriaSpecification#LEFT_JOIN
     */
    protected static CriteriaElement alias(String associationPath, String alias, int joinType) {
        return new Alias(associationPath, alias, joinType);
    }

    protected static ICriteriaElement ascOrder(String propertyName) {
        return Order.asc(propertyName);
    }

    protected static ICriteriaElement descOrder(String propertyName) {
        return Order.desc(propertyName);
    }

    protected static CriteriaElement idEquals(Object value) {
        return new IdEquals(value);
    }

    protected static CriteriaElement equals(String propertyName, Object value) {
        if (value == null)
            return new IsNull(propertyName);
        else
            return new Equals(propertyName, value);
    }

    protected static CriteriaElement notEquals(String propertyName, Object value) {
        if (value == null)
            return new IsNotNull(propertyName);
        else
            return new NotEquals(propertyName, value);
    }

    protected static CriteriaElement _idEquals(Object value) {
        if (value == null)
            return null;
        return idEquals(value);
    }

    protected static CriteriaElement _equals(String propertyName, Object value) {
        if (value == null)
            return null;
        return equals(propertyName, value);
    }

    protected static CriteriaElement _notEquals(String propertyName, Object value) {
        if (value == null)
            return null;
        return notEquals(propertyName, value);
    }

    protected static CriteriaElement like(String propertyName, String pattern) {
        if (pattern == null)
            return new IsNull(propertyName);
        else
            return new Like(propertyName, pattern);
    }

    protected static CriteriaElement like(String propertyName, String pattern, MatchMode matchMode) {
        if (pattern == null)
            return new IsNull(propertyName);
        else
            return new Like(propertyName, pattern, matchMode);
    }

    protected static CriteriaElement likeIgnoreCase(String propertyName, String pattern) {
        if (pattern == null)
            return new IsNull(propertyName);
        else
            return new Like(true, propertyName, pattern);
    }

    protected static CriteriaElement likeIgnoreCase(String propertyName, String pattern, MatchMode matchMode) {
        if (pattern == null)
            return new IsNull(propertyName);
        else
            return new Like(true, propertyName, pattern, matchMode);
    }

    protected static CriteriaElement _like(String propertyName, String pattern) {
        if (pattern == null)
            return null;
        return like(propertyName, pattern);
    }

    protected static CriteriaElement _likeIgnoreCase(String propertyName, String pattern) {
        if (pattern == null)
            return null;
        return likeIgnoreCase(propertyName, pattern);
    }

    protected static CriteriaElement greaterThan(String propertyName, Object value) {
        if (value == null)
            throw new NullPointerException("value");
        return new GreaterThan(propertyName, value);
    }

    protected static CriteriaElement greaterOrEquals(String propertyName, Object value) {
        if (value == null)
            throw new NullPointerException("value");
        return new GreaterOrEquals(propertyName, value);
    }

    protected static CriteriaElement lessThan(String propertyName, Object value) {
        if (value == null)
            throw new NullPointerException("value");
        return new LessThan(propertyName, value);
    }

    protected static CriteriaElement lessOrEquals(String propertyName, Object value) {
        if (value == null)
            throw new NullPointerException("value");
        return new LessOrEquals(propertyName, value);
    }

    protected static CriteriaElement between(String propertyName, Object lo, Object hi) {
        if (lo == null)
            throw new NullPointerException("lo");
        if (hi == null)
            throw new NullPointerException("hi");
        return new Between(propertyName, lo, hi);
    }

    protected static CriteriaElement _greaterThan(String propertyName, Object value) {
        if (value == null)
            return null;
        return greaterThan(propertyName, value);
    }

    protected static CriteriaElement _greaterOrEquals(String propertyName, Object value) {
        if (value == null)
            return null;
        return greaterOrEquals(propertyName, value);
    }

    protected static CriteriaElement _lessThan(String propertyName, Object value) {
        if (value == null)
            return null;
        return lessThan(propertyName, value);
    }

    protected static CriteriaElement _lessOrEquals(String propertyName, Object value) {
        if (value == null)
            return null;
        return lessOrEquals(propertyName, value);
    }

    protected static CriteriaElement _between(String propertyName, Object lo, Object hi) {
        if (lo == null && hi == null)
            return null;
        if (lo == null)
            return lessOrEquals(propertyName, hi);
        if (hi == null)
            return greaterOrEquals(propertyName, lo);
        return between(propertyName, lo, hi);
    }

    protected static CriteriaElement in(String propertyName, Object... values) {
        return new InArray(propertyName, values);
    }

    protected static CriteriaElement in(String propertyName, Collection<?> values) {
        return new InCollection(propertyName, values);
    }

    protected static CriteriaElement _in(String propertyName, Object... values) {
        if (values == null)
            return null;
        return in(propertyName, values);
    }

    protected static CriteriaElement _in(String propertyName, Collection<?> values) {
        if (values == null)
            return null;
        return in(propertyName, values);
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

    protected static CriteriaElement allEquals(Map<?, ?> propertyNameValues) {
        return new AllEquals(propertyNameValues);
    }

    protected static CriteriaElement isEmpty(String propertyName) {
        return new IsEmpty(propertyName);
    }

    protected static CriteriaElement isNotEmpty(String propertyName) {
        return new IsNotEmpty(propertyName);
    }

    protected static CriteriaElement sizeEquals(String propertyName, int size) {
        return new SizeEquals(propertyName, size);
    }

    protected static CriteriaElement sizeNotEquals(String propertyName, int size) {
        return new SizeNotEquals(propertyName, size);
    }

    protected static CriteriaElement sizeGreaterThan(String propertyName, int size) {
        return new SizeGreaterThan(propertyName, size);
    }

    protected static CriteriaElement sizeLessThan(String propertyName, int size) {
        return new SizeLessThan(propertyName, size);
    }

    protected static CriteriaElement sizeGreaterOrEquals(String propertyName, int size) {
        return new SizeGreaterOrEquals(propertyName, size);
    }

    protected static CriteriaElement sizeLessOrEquals(String propertyName, int size) {
        return new SizeLessOrEquals(propertyName, size);
    }

    protected static CriteriaElement naturalId() {
        return new NaturalId();
    }

    protected static Conjunction conj(ICriteriaElement... elements) {
        return new Conjunction(elements);
    }

    protected static Disjunction disj(ICriteriaElement... elements) {
        return new Disjunction(elements);
    }

    protected static CriteriaElement and(CriteriaElement lhs, CriteriaElement rhs) {
        return And.of(lhs, rhs);
    }

    protected static CriteriaElement or(CriteriaElement lhs, CriteriaElement rhs) {
        return Or.of(lhs, rhs);
    }

    protected static CriteriaElement not(CriteriaElement expression) {
        return Not.of(expression);
    }

    // Projections.

    protected static ProjectionList projectionList() {
        return new ProjectionList();
    }

    protected static ProjectionList proj(ProjectionElement... elements) {
        return new ProjectionList(elements);
    }

    protected static ProjectionElement distinct(ProjectionElement proj) {
        return new Distinct(proj);
    }

    protected static ProjectionElement rowCount() {
        return new RowCountProjection();
    }

    protected static ProjectionElement count(String propertyName) {
        return new CountProjection(propertyName);
    }

    protected static ProjectionElement countDistinct(String propertyName) {
        return new CountProjection(propertyName, true);
    }

    protected static ProjectionElement max(String propertyName) {
        return new MaxProjection(propertyName);
    }

    protected static ProjectionElement min(String propertyName) {
        return new MinProjection(propertyName);
    }

    protected static ProjectionElement avg(String propertyName) {
        return new AvgProjection(propertyName);
    }

    protected static ProjectionElement sum(String propertyName) {
        return new SumProjection(propertyName);
    }

    protected static ProjectionElement sqlProjection(String sql, String[] columnAliases, Type[] types) {
        return new SQLProjection(sql, columnAliases, types);
    }

    protected static ProjectionElement sqlGroupProjection(String sql, String groupBy, String[] columnAliases,
            Type[] types) {
        return new SQLProjection(sql, groupBy, columnAliases, types);
    }

    protected static ProjectionElement property(String propertyName) {
        return new PropertyProjection(propertyName);
    }

    protected static ProjectionElement group(String propertyName) {
        return new GroupPropertyProjection(propertyName);
    }

    protected static ProjectionElement _property(String propertyName, Object val) {
        if (val == null)
            return null;
        return new PropertyProjection(propertyName);
    }

    protected static ProjectionElement _group(String propertyName, Object val) {
        if (val == null)
            return null;
        return new GroupPropertyProjection(propertyName);
    }

    protected static ProjectionElement id() {
        return new IdentifierProjection();
    }

    protected static ProjectionElement alias(ProjectionElement projection, String alias) {
        return new AliasedProjection(projection, alias);
    }

}
