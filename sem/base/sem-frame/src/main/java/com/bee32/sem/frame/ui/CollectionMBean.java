package com.bee32.sem.frame.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import javax.annotation.concurrent.NotThreadSafe;
import javax.free.CreateException;
import javax.free.UnexpectedException;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.FunctorException;
import org.apache.commons.collections15.functors.InstantiateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.faces.utils.FacesContextSupport;

@NotThreadSafe
public abstract class CollectionMBean<T>
        extends FacesContextSupport
        implements IEnclosingContext, Serializable {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(CollectionMBean.class);

    final Object context;
    final Factory<T> factory;
    boolean copyMode;
    T openedObject;

    public CollectionMBean(Factory<T> factory, Object context) {
        if (factory == null)
            throw new NullPointerException("factory");
        this.factory = factory;
        this.context = context;
    }

    public CollectionMBean(Class<T> elementType, Object context) {
        if (elementType == null)
            throw new NullPointerException("elementType");
        this.factory = InstantiateFactory.getInstance(elementType, null, null);
        this.context = context;
    }

    public T createElement()
            throws CreateException {
        T obj;
        try {
            obj = factory.create();
        } catch (FunctorException e) {
            throw new CreateException(e.getMessage(), e);
        }

        if (obj instanceof BaseDto<?, ?>) {
            BaseDto<?, ?> dto = (BaseDto<?, ?>) obj;
            dto.create();
        }

        if (context != null && obj instanceof IEnclosedObject) {
            @SuppressWarnings("unchecked")
            IEnclosedObject<Object> enclosed = ((IEnclosedObject<Object>) obj);
            Object enclosingObject = context;
            if (context instanceof IEnclosingContext)
                enclosingObject = ((IEnclosingContext) context).getEnclosingObject();
            try {
                enclosed.setEnclosingObject(enclosingObject);
            } catch (ClassCastException e) {
                logger.warn("Failed to cast enclosing object.");
            }
        }
        return obj;
    }

    public final boolean isCopyMode() {
        return copyMode;
    }

    public final void setCopyMode(boolean copyMode) {
        this.copyMode = copyMode;
    }

    static final Method cloneMethod;
    static {
        try {
            cloneMethod = Object.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }
    }

    protected T copyObject(T value) {
        if (!copyMode)
            return value;
        // System.out.println("Copy: " + value);
        if (value == null)
            return null;
        if (value instanceof Cloneable) {
            try {
                return (T) cloneMethod.invoke(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to clone " + value, e);
            }
        }
        if (value instanceof Serializable) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(out).writeObject(value);
                byte[] data = out.toByteArray();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                return (T) new ObjectInputStream(in).readObject();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                throw new UnexpectedException(e.getMessage(), e);
            }
        }
        throw new UnsupportedOperationException("Can't copy object of " + value.getClass());
    }

    public T getOpenedObject() {
        return openedObject;
    }

    @Override
    public Object getEnclosingObject() {
        return getOpenedObject();
    }

}
