package com.bee32.ape.engine.base;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.AbstractQuery;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.query.Query;

import com.bee32.ape.engine.identity.ActivitiOrderUtils;
import com.bee32.ape.engine.identity.UserEntityUtils;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.entity.Entity;

@SuppressWarnings("unchecked")
public abstract class AbstractApeQuery<query_t extends Query<?, ?>, pojo_t>
        implements Query<query_t, pojo_t>, IApeActivitiAdapter {

    Class<? extends Entity<?>> icsfEntityType;

    String sqlOrderProperty;
    boolean reverseOrder = false;

    public AbstractApeQuery(Class<? extends Entity<?>> icsfEntityType) {
        if (icsfEntityType == null)
            throw new NullPointerException("icsfEntityType");
        this.icsfEntityType = icsfEntityType;
    }

    public AbstractApeQuery(Class<? extends Entity<?>> icsfEntityType, AbstractQuery<?, ?> o) {
        if (icsfEntityType == null)
            throw new NullPointerException("icsfEntityType");
        if (o == null)
            throw new NullPointerException("o");

        this.icsfEntityType = icsfEntityType;

        String orderBy = o.getOrderBy();
        if (orderBy != null) {
            if (!ActivitiOrderUtils.isAscending(orderBy))
                reverseOrder = true;

            UserQueryProperty property = UserEntityUtils.getOrderProperty(orderBy);
            if (property == UserQueryProperty.USER_ID)
                sqlOrderProperty = "name";

            else if (property == UserQueryProperty.FIRST_NAME)
                sqlOrderProperty = "label";
            else if (property == UserQueryProperty.LAST_NAME)
                // order by last name isn't supported.
                ;
            else if (property == UserQueryProperty.EMAIL)
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

    protected String getOrderProperty() {
        return sqlOrderProperty;
    }

    protected void setOrderProperty(String orderProperty) {
        this.sqlOrderProperty = orderProperty;
    }

    protected boolean isReverseOrder() {
        return reverseOrder;
    }

    protected void setReverseOrder(boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
    }

    CriteriaComposite composeAll() {
        CriteriaComposite composite = compose();
        if (sqlOrderProperty != null) {
            Order order;
            if (reverseOrder)
                order = Order.desc(sqlOrderProperty);
            else
                order = Order.asc(sqlOrderProperty);
            composite.add(order);
        }
        return composite;
    }

    protected abstract CriteriaComposite compose();

    protected abstract pojo_t icsf2activiti(Entity<?> icsfEntity);

    protected List<pojo_t> icsf2activiti(List<Entity<?>> icsfEntityList) {
        List<pojo_t> pojoList = new ArrayList<pojo_t>(icsfEntityList.size());
        for (Entity<?> icsfEntity : icsfEntityList) {
            pojo_t pojo = icsf2activiti(icsfEntity);
            pojoList.add(pojo);
        }
        return pojoList;
    }

    @Override
    public long count() {
        ICriteriaElement composite = composeAll();
        int count = ctx.data.access(icsfEntityType).count(composite);
        return count;
    }

    @Override
    public pojo_t singleResult() {
        ICriteriaElement composite = composeAll();
        Entity<?> _first = ctx.data.access(icsfEntityType).getFirst(composite);
        if (_first == null)
            return null;
        else
            return icsf2activiti(_first);
    }

    @Override
    public List<pojo_t> list() {
        ICriteriaElement composite = composeAll();
        List<Entity<?>> list = ctx.data.access(icsfEntityType).list(composite);
        return icsf2activiti(list);
    }

    @Override
    public List<pojo_t> listPage(int firstResult, int maxResults) {
        CriteriaComposite composite = composeAll();
        composite.add(new Limit(firstResult, maxResults));
        List<Entity<?>> list = ctx.data.access(icsfEntityType).list(composite);
        return icsf2activiti(list);
    }

}
