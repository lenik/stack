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

import com.bee32.plover.arch.util.ICopyable;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.IEnclosingContext;
import com.bee32.plover.arch.util.dto.BaseDto;

@NotThreadSafe
public abstract class CollectionMBean<T>
        implements IEnclosingContext, Serializable {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(CollectionMBean.class);

    protected final Class<T> elementType;
    final Factory<T> factory;

    protected final Object context;
    boolean copyMode;
    T openedObject;

    public CollectionMBean(Class<T> elementType, Object context) {
        if (elementType == null)
            throw new NullPointerException("elementType");
        this.factory = InstantiateFactory.getInstance(elementType, null, null);
        this.elementType = elementType;
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

        BaseDto<?> dto = null;
        if (obj instanceof BaseDto<?>)
            dto = (BaseDto<?>) obj;

        if (dto != null)
            dto.create();

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
        return _copyObject(value);
    }

    protected T _copyObject(T value) {
        // System.out.println("Copy: " + value);
        if (value == null)
            return null;
        if (value instanceof ICopyable) {
            ICopyable copyable = (ICopyable) value;
            T copy = copyable.copy();
            return copy;
        }
        if (value instanceof Cloneable) {
            logger.warn("Copy by clone: " + value);
            try {
                return (T) cloneMethod.invoke(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to clone " + value, e);
            }
        }
        if (value instanceof Serializable) {
            logger.warn("Copy by serialization: " + value);
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
