package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;

public class Order
        extends SpecialCriteriaElement {

    private static final long serialVersionUID = 1L;

    org.hibernate.criterion.Order order;

    Order(org.hibernate.criterion.Order order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        if ((options & (NO_ORDER | OPTIM_COUNT)) != 0)
            return;
        criteria.addOrder(order);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(order ");
        out.append(order.toString()); // "property asc|desc"
        out.append(")");
    }

    /**
     * Ascending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order asc(String propertyName) {
        return new Order(org.hibernate.criterion.Order.asc(propertyName));
    }

    /**
     * Descending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order desc(String propertyName) {
        return new Order(org.hibernate.criterion.Order.desc(propertyName));
    }

}
