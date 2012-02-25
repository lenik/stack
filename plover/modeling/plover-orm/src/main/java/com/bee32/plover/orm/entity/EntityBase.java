package com.bee32.plover.orm.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.bean.BeanPropertyAccessor;
import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.arch.naming.INamed;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.model.Model;

public abstract class EntityBase<K extends Serializable>
        extends Model
        implements IEntity<K>, INamed, ILabelledEntry, IPopulatable {

    private static final long serialVersionUID = 1L;

    protected boolean autoId;
    protected String name;

    public EntityBase() {
        super(null, false);
        createTransients();
    }

    public EntityBase(String name) {
        super(name, false);
        this.name = name;
        createTransients();
    }

    String _internalName() {
        return this.name;
    }

    void _internalName(String name) {
        this.name = name;
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        createTransients();
    }

    protected void createTransients() {
    }

    @Override
    public String refName() {
        K id = getId();
        if (id == null)
            return null;
        else
            return id.toString();
    }

    @Override
    public String getEntryLabel() {
        StringBuilder buf = new StringBuilder();
        buf.append(ClassUtil.getParameterizedTypeName(this));
        buf.append(' ');
        formatEntryText(buf);
        return buf.toString();
    }

    protected void formatEntryText(StringBuilder buf) {
        buf.append(getId());
    }

    /**
     * Test if this entity is locked.
     *
     * A locked entity can't be modified or deleted.
     * <p>
     * When override this method, you must
     *
     * <pre>
     * return super.{@link #isLocked()};
     * </pre>
     *
     * at the end.
     *
     * @return <code>true</code> if this entity is locked.
     */
    public boolean isLocked() {
        return false;
    }

    @SuppressWarnings("unchecked")
    protected <T> T cast(Object thisLike) {
        if (thisLike == null)
            return null;

        Object derived = getClass().cast(thisLike);

        return (T) derived;
    }

    protected <C extends IParentAware<P>, P> Set<C> attachCopy(Class<C> childClass, Set<?> children) {
        @SuppressWarnings("unchecked")
        P parent = (P) this;
        return attachCopy(parent, childClass, children);
    }

    protected <C extends IParentAware<P>, P> Set<C> attachCopy(P parent, Class<C> childClass, Set<?> children) {
        Set<C> copy = new HashSet<C>();
        for (Object _child : children) {
            C child = childClass.cast(_child);
            child.setParent(parent);
            copy.add(child);
        }
        return copy;
    }

    protected static <T> Iterable<T> flyOver(Collection<? extends T> it) {
        return new ArrayList<T>(it);
    }

    protected static <bean_t, property_t> IPropertyAccessor<property_t> _property_(Class<bean_t> beanClass,
            String propertyName) {
        return BeanPropertyAccessor.access(beanClass, propertyName);
    }

    @SafeVarargs
    protected static <T> List<T> listOf(T... args) {
        return Arrays.asList(args);
    }

}
