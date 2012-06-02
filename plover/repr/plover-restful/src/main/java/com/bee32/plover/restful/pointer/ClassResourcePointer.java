package com.bee32.plover.restful.pointer;

public class ClassResourcePointer
        extends AbstractResourcePointer {

    private final Class<?> contextClass;
    private final String href;

    public ClassResourcePointer(Class<?> contextClass, String href) {
        if (contextClass == null)
            throw new NullPointerException("contextClass");
        if (href == null)
            throw new NullPointerException("href");
        this.contextClass = contextClass;
        this.href = href;
    }

    @Override
    public String instantiate(String context) {

        return null;
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public String getHref() {
        return href;
    }

}
