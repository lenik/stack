package com.bee32.ape.engine.identity.composite;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.query.Query;

public abstract class AbstractCompositeQuery<query_t extends Query<?, ? extends U>, U>
        extends ArrayList<query_t>
        implements Query<query_t, U> {

    private static final long serialVersionUID = 1L;

    public AbstractCompositeQuery() {
    }

    public AbstractCompositeQuery(AbstractCompositeQuery<query_t, U> o) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public query_t asc() {
        for (query_t item : this)
            item.asc();
        return (query_t) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public query_t desc() {
        for (query_t item : this)
            item.desc();
        return (query_t) this;
    }

    @Override
    public long count() {
        long total = 0;
        for (query_t item : this)
            total += item.count();
        return total;
    }

    @Override
    public U singleResult() {
        U single = null;
        for (query_t item : this) {
            U result = item.singleResult();
            if (single == null)
                single = result;
            else
                throw new ActivitiException("Too many results.");
        }
        return single;
    }

    @Override
    public List<U> list() {
        List<U> all = new ArrayList<U>();
        for (query_t item : this) {
            List<? extends U> sublist = item.list();
            all.addAll(sublist);
        }
        return all;
    }

    @Override
    public List<U> listPage(int firstResult, int maxResults) {
        List<U> all = new ArrayList<U>();
        for (query_t item : this) {
            int offset = firstResult + all.size();
            int remaining = maxResults - all.size();
            List<? extends U> sublist = item.listPage(offset, remaining);
            all.addAll(sublist);
        }
        return all;
    }

}
