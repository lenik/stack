package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

class Order
        extends CriteriaElement {

    org.hibernate.criterion.Order order;

    Order(org.hibernate.criterion.Order order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    @Override
    public void apply(Criteria criteria) {
        criteria.addOrder(order);
    }

    @Override
    protected Criterion buildCriterion() {
        return null;
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
