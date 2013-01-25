package com.bee32.sem.process.ape;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.AbstractQuery;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.query.Query;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.entity.Entity;

@SuppressWarnings("unchecked")
public abstract class AbstractApeQuery<query_t extends Query<?, ?>, pojo_t>
        implements Query<query_t, pojo_t>, IApeActivitiAdapter {

    Class<? extends Entity<?>> semEntityType;

    String orderProperty;
    boolean reverseOrder = false;

    public AbstractApeQuery(Class<? extends Entity<?>> semEntityType) {
        if (semEntityType == null)
            throw new NullPointerException("semEntityType");
        this.semEntityType = semEntityType;
    }

    public AbstractApeQuery(Class<? extends Entity<?>> semEntityType, AbstractQuery<?, ?> o) {
        if (semEntityType == null)
            throw new NullPointerException("semEntityType");
        if (o == null)
            throw new NullPointerException("o");

        this.semEntityType = semEntityType;

        String orderBy = o.getOrderBy();
        // parse orderBy...
        if (orderBy != null) {
            orderBy = orderBy.trim();
            int pos = orderBy.indexOf(' ');
            String column;
            if (pos == -1) {
                column = orderBy;
            } else {
                column = orderBy.substring(0, pos);
                switch (orderBy.substring(pos + 1).trim().toLowerCase()) {
                case "desc":
                    reverseOrder = true;
                }
            }

            if (UserQueryProperty.USER_ID.getName().equals(column))
                orderProperty = "name";
            else if (UserQueryProperty.FIRST_NAME.getName().equals(column))
                orderProperty = "label";
            else if (UserQueryProperty.LAST_NAME.getName().equals(column))
                // order by last name isn't supported.
                ;
            else if (UserQueryProperty.EMAIL.getName().equals(column))
                // order by email isn't supported.
                ;
            else
                // ignored unknown column.
                ;
        }
    }

    @Override
    public query_t asc() {
        reverseOrder = false;
        return (query_t) this;
    }

    @Override
    public query_t desc() {
        reverseOrder = true;
        return (query_t) this;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    CriteriaComposite composeAll() {
        CriteriaComposite composite = compose();
        if (orderProperty != null) {
            Order order;
            if (reverseOrder)
                order = Order.desc(orderProperty);
            else
                order = Order.asc(orderProperty);
            composite.add(order);
        }
        return composite;
    }

    protected abstract CriteriaComposite compose();

    protected abstract pojo_t sem2activiti(Entity<?> semEntity);

    protected List<pojo_t> sem2activiti(List<Entity<?>> semEntityList) {
        List<pojo_t> pojoList = new ArrayList<pojo_t>(semEntityList.size());
        for (Entity<?> semEntity : semEntityList) {
            pojo_t pojo = sem2activiti(semEntity);
            pojoList.add(pojo);
        }
        return pojoList;
    }

    @Override
    public long count() {
        ICriteriaElement composite = composeAll();
        int count = ctx.data.access(semEntityType).count(composite);
        return count;
    }

    @Override
    public pojo_t singleResult() {
        ICriteriaElement composite = composeAll();
        Entity<?> _first = ctx.data.access(semEntityType).getFirst(composite);
        if (_first == null)
            return null;
        else
            return sem2activiti(_first);
    }

    @Override
    public List<pojo_t> list() {
        ICriteriaElement composite = composeAll();
        List<Entity<?>> list = ctx.data.access(semEntityType).list(composite);
        return sem2activiti(list);
    }

    @Override
    public List<pojo_t> listPage(int firstResult, int maxResults) {
        CriteriaComposite composite = composeAll();
        composite.add(new Limit(firstResult, maxResults));
        List<Entity<?>> list = ctx.data.access(semEntityType).list(composite);
        return sem2activiti(list);
    }

}
