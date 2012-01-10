package com.bee32.plover.orm.entity;

import com.bee32.plover.orm.util.ErrorResult;

/**
 * @see IEntityLifecycle
 */
public interface IEntityLifecycleAddon {

    ErrorResult entityCreate(Entity<?> entity);

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
    ErrorResult entityValidate(Entity<?> entity);

    /**
     * Test if this entity is allowed to be modified.
     * <p>
     * When override this method, you must
     *
     * <pre>
     * return super.{@link #entityCheckModify()};
     * </pre>
     *
     * at the end.
     *
     * @return Non-<code>null</code> error result to prevent this entity from being modified.
     */
    ErrorResult entityCheckModify(Entity<?> entity);

    /**
     * Test if this entity is allowed to be modified.
     * <p>
     * When override this method, you must
     *
     * <pre>
     * return super.{@link #entityCheckDelete()};
     * </pre>
     *
     * at the end.
     *
     * @return Non-<code>null</code> error result to prevent this entity from being deleted.
     */
    ErrorResult entityCheckDelete(Entity<?> entity);

    void entityUpdated(Entity<?> entity);

    /**
     * Called after the entity is removed from the database.
     */
    void entityDeleted(Entity<?> entity);

}
