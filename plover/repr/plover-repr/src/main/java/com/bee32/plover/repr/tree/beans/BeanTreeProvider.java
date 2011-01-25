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
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

            MethodDescriptor[] methods = beanInfo.getMethodDescriptors();

        } catch (Exception e) {
            throw new BuildException(e.getMessage(), e);
        }
        return null;
    }

}
