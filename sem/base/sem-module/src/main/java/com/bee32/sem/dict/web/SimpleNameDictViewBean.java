package com.bee32.sem.dict.web;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class SimpleNameDictViewBean
        extends SimpleEntityViewBean
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    String title;

    public SimpleNameDictViewBean()
            throws ClassNotFoundException {
        this(getEntityCalss());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private SimpleNameDictViewBean(Class clazz) {
        super(clazz, (Class) EntityUtil.getDtoType(clazz), 0);
        HttpServletRequest request = ThreadServletContext.getRequest();
        this.title = request.getParameter("title");
    }

    static Class<? extends NameDict> getEntityCalss()
            throws ClassNotFoundException {
        HttpServletRequest request = ThreadServletContext.getRequest();
        String entity = request.getParameter("entityClass");
        Class<? extends NameDict> entityClass = (Class<? extends NameDict>) ABBR.expand(entity);
        return entityClass;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}