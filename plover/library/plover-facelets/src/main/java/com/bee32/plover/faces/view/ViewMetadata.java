package com.bee32.plover.faces.view;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
@PerView
public/* final */class ViewMetadata
        implements Serializable, DisposableBean {

    private static final long serialVersionUID = 1L;

    static final Map<Object, ViewMetadata> instanceMap = new HashMap<Object, ViewMetadata>();

    Object id = System.identityHashCode(this);
    Date createdTime = new Date();
    Date lastAccessTime = createdTime;
    final Map<String, Object> attributeMap = new HashMap<String, Object>();
    Map<Object, Object> viewBeans = new LinkedHashMap<Object, Object>();

    public ViewMetadata() {
        instanceMap.put(getId(), this);
    }

    public Object getId() {
        return id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void access(ViewBean source) {
        lastAccessTime = new Date();
        // source isn't used yet.
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public <T> T getAttribute(String name) {
        T value = (T) attributeMap.get(name);
        return value;
    }

    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    public void addViewBean(Object viewBean) {
        int id = System.identityHashCode(viewBean);
        viewBeans.put(id, viewBean);
    }

    public void removeViewBean(Object viewBean) {
        int id = System.identityHashCode(viewBean);
        viewBeans.remove(id);
    }

    public Collection<?> getViewBeans() {
        return viewBeans.values();
    }

    public List<?> getViewBeans(Class<?>... interfaces) {
        if (interfaces.length == 0)
            return new ArrayList<Object>(getViewBeans());
        List<Object> interestings = new ArrayList<Object>();
        for (Object viewBean : viewBeans.values()) {
            Class<?> viewBeanType = viewBean.getClass();
            boolean interesting = true;
            for (Class<?> iface : interfaces)
                if (!iface.isAssignableFrom(viewBeanType)) {
                    interesting = false;
                    break;
                }
            if (!interesting)
                continue;
            interestings.add(viewBean);
        }
        return interestings;
    }

    @Override
    public void destroy()
            throws Exception {
        instanceMap.remove(getId());
    }

}
