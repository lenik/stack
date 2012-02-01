package com.bee32.sem.frame.ui;

import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.free.IllegalUsageException;

public class ELListMBean<T>
        extends ListMBean<T> {

    private static final long serialVersionUID = 1L;

    final Object root;
    final String property;

    transient List<T> list;

    public ELListMBean(Class<T> elementType, Object root, String property) {
        super(elementType);
        this.root = root;
        this.property = property;
    }

    @Override
    public synchronized List<T> getList() {
        if (list == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ELContext elContext = facesContext.getELContext();
            ELResolver elResolver = elContext.getELResolver();
            Object value = elResolver.getValue(elContext, root, property);
            if (!(value instanceof List<?>))
                throw new IllegalUsageException("Property doesn't resolve to a List: " + property);

            @SuppressWarnings("unchecked")
            List<T> listValue = (List<T>) value;
            list = listValue;
        }
        return list;
    }

}
