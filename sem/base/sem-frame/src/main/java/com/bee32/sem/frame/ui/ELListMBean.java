package com.bee32.sem.frame.ui;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.free.IllegalUsageException;

import org.apache.commons.collections15.Factory;

public class ELListMBean<T>
        extends ListMBean<T> {

    private static final long serialVersionUID = 1L;

    final Object root;
    final String property;

    transient List<T> list;

    public ELListMBean(Factory<T> elementFactory, Object root, String property) {
        super(elementFactory, root);
        this.root = root;
        this.property = property; // "${obj." + property + "}";
    }

    public ELListMBean(Class<T> elementType, Object root, String property) {
        super(elementType, root);
        this.root = root;
        this.property = property; // "${obj." + property + "}";
    }

    @Override
    public synchronized List<T> getList() {
        list = null; // TODO optim: when to invalidate the EL list?
        if (list == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // ExpressionFactory factory = facesContext.getApplication().getExpressionFactory();
            ELContext elContext = facesContext.getELContext();
            // ValueExpression expr = factory.createValueExpression(property, List.class);
            Object value;
            // value = expr.getValue(elContext);

            ELResolver elResolver = elContext.getELResolver();
            value = root;
            StringTokenizer tokens = new StringTokenizer(property, ".");
            while (value != null && tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                value = elResolver.getValue(elContext, value, token);
            }
            if (value == null) {
                // throw new IllegalUsageException("Property resolved to null: " + property);
		logger.debug("Property resolved to null: " + property + ", a read-only empty list is created.");
		return Collections.emptyList();
            }

            if (!(value instanceof List<?>))
                throw new IllegalUsageException("Property doesn't resolve to a List: " + property);

            @SuppressWarnings("unchecked")
            List<T> listValue = (List<T>) value;
            list = listValue;
        }
        return list;
    }

}
