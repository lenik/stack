package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.arch.naming.INamed;
import com.bee32.plover.model.Model;
import com.bee32.plover.orm.util.ErrorResult;

public abstract class EntityBase<K extends Serializable>
        extends Model
        implements IEntity<K>, INamed, IPopulatable {

    private static final long serialVersionUID = 1L;

    public EntityBase() {
        super();
    }

    public EntityBase(String name) {
        super(name);
    }

    String _internalName() {
        return this.name;
    }

    void _internalName(String name) {
        this.name = name;
    }

    @Override
    public String refName() {
        K id = getId();
        if (id == null)
            return null;
        else
            return id.toString();
    }

    /**
     * Validate this entity.
     * <p>
     * Entity can only be saved or updated if it's validated.
     * <p>
     * When override this method, you should `return super.validate()` at the end.
     *
     * @return <code>null</code> if validated, or non-<code>null</code> error result contains the
     *         error message.
     */
    protected ErrorResult validate() {
        return null;
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
    protected boolean isLocked() {
        return false;
    }

    /**
     * Test if this entity is allowed to be modified.
     * <p>
     * When override this method, you must
     *
     * <pre>
     * return super.{@link #checkModify()};
     * </pre>
     *
     * at the end.
     *
     * @return <code>false</code> to prevent this entity from being modified.
     */
    protected ErrorResult checkModify() {
        if (isLocked())
            return ErrorResult.error("Entity is locked");
        return null;
    }

    /**
     * Test if this entity is allowed to be modified.
     * <p>
     * When override this method, you must
     *
     * <pre>
     * return super.{@link #checkDelete()};
     * </pre>
     *
     * at the end.
     *
     * @return <code>false</code> to prevent this entity from being deleted.
     */
    protected ErrorResult checkDelete() {
        if (isLocked())
            return ErrorResult.error("Entity is locked");
        return null;
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

}
