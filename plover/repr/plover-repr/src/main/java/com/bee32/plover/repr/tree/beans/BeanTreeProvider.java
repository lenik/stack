package com.bee32.plover.repr.tree.beans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;

import com.bee32.plover.repr.tree.BuildException;
import com.bee32.plover.repr.tree.IReprTree;
import com.bee32.plover.repr.tree.TreeProvider;

public class BeanTreeProvider
        implements TreeProvider {

    @Override
    public IReprTree build(Class<?> clazz, Object obj)
            throws BuildException {
        DefaultReprTree tree = new DefaultReprTree();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (Exception e) {
            throw new BuildException(e.getMessage(), e);
        }
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        MethodDescriptor[] methods = beanInfo.getMethodDescriptors();

        // TODO
        for (PropertyDescriptor property : properties)
            tree.getMap().put(property.getName(), property);
        for (MethodDescriptor method : methods)
            tree.getMap().put(method.getName(), method);
        return tree;
    }

}
