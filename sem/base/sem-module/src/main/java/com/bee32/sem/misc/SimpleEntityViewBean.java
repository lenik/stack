package com.bee32.sem.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.IllegalUsageException;

import org.primefaces.model.LazyDataModel;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public abstract class SimpleEntityViewBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    /** Delete entities with user lock */
    protected static final int DELETE_FORCE = 1;
    /** Detach the entity before deletion */
    protected static final int DELETE_DETACH = 2;

    /** Force to update entities with user lock */
    protected static final int SAVE_FORCE = 1;
    /** Save entities with user lock on */
    protected static final int SAVE_LOCKED = 2;
    /** Save entities with user lock off. */
    protected static final int SAVE_UNLOCKED = 4;
    /** Save entities with user lock off. */
    protected static final int SAVE_FORCE_UNLOCKED = SAVE_FORCE | SAVE_UNLOCKED;
    /** Change the entities' owner to current user before save */
    protected static final int SAVE_CHOWN = 8;

    protected Class<? extends Entity<?>> entityClass;
    protected Class<? extends EntityDto<?, ?>> dtoClass;

    EntityDataModelOptions<?, ?> options;
    LazyDataModel<?> dataModel;

    protected int saveFlags = 0;
    protected int deleteFlags = 0;

    public <E extends Entity<?>, D extends EntityDto<? super E, ?>> //
    /*    */SimpleEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection,
            ICriteriaElement... criteriaElements) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;

        List<ICriteriaElement> criteriaList = new ArrayList<ICriteriaElement>(criteriaElements.length);
        for (ICriteriaElement element : criteriaElements)
            criteriaList.add(element);
        EntityDataModelOptions<E, D> options = new EntityDataModelOptions<E, D>(//
                entityClass, dtoClass, selection, criteriaList);

        this.dataModel = UIHelper.buildLazyDataModel(options);
        this.options = options;

        refreshCount();
    }

    protected void refreshCount() {
        List<ICriteriaElement> list = getCriteriaElements();
        ICriteriaElement[] criteria = list.toArray(new ICriteriaElement[0]);
        int count = serviceFor(entityClass).count(criteria);
        dataModel.setRowCount(count);
    }

    public LazyDataModel<?> getDataModel() {
        return dataModel;
    }

    protected final List<ICriteriaElement> getCriteriaElements() {
        return options.getCriteriaElements();
    }

    protected final void setCriteriaElements(ICriteriaElement... criteriaElements) {
        options.setCriteriaElements(criteriaElements);
    }

    protected final void setCriteriaElements(List<ICriteriaElement> criteriaElements) {
        options.setCriteriaElements(criteriaElements);
    }

    /**
     * Show (or switch to) the real view.
     *
     * You need to override this method to set extra view states.
     *
     * @param viewName
     *            Standard view name.
     * @see StandardViews#LIST
     * @see StandardViews#CONTENT
     * @see StandardViews#CREATE_FORM
     * @see StandardViews#EDIT_FORM
     */
    protected void showView(String viewName) {
    }

    @Operation
    public void showIndex() {
        setActiveObject(null);
        showView(StandardViews.LIST);
    }

    @Operation
    public void showContent() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelectedDtos(-1);
        showView(StandardViews.CONTENT);
    }

    @Operation
    public void showCreateForm() {
        EntityDto<?, ?> entityDto;
        try {
            entityDto = dtoClass.newInstance();
            entityDto = entityDto.create();
        } catch (Exception e) {
            uiLogger.error("无法创建对象", e);
            return;
        }
        setActiveObject(entityDto);
        showView(StandardViews.CREATE_FORM);
    }

    @Operation
    public void showEditForm() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelectedDtos(-1);
        showView(StandardViews.EDIT_FORM);
    }

    @Operation
    public void save() {
        save(saveFlags);
    }

    protected void save(int saveFlags) {
        checkSaveFlags(saveFlags);

        if (getActiveObjects().isEmpty()) {
            uiLogger.error("没有需要保存的对象!");
            return;
        }

        Collection<? extends Entity<?>> entities;
        try {
            entities = unmarshalDtos(getActiveObjects(), false);
        } catch (NullPointerException e) {
            uiLogger.error("有些需要保存的对象已失效，请刷新页面重试。", e);
            return;
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        User me = SessionUser.getInstance().getInternalUserOpt();
        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : entities) {
            EntityFlags flags = EntityAccessor.getFlags(entity);

            if ((saveFlags & SAVE_FORCE) != 0) {
                if (flags.isUserLock())
                    flags.setUserLock(false);
            }

            boolean locked = EntityAccessor.isLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }

            if ((saveFlags & SAVE_LOCKED) != 0)
                flags.setUserLock(true);

            if ((saveFlags & SAVE_UNLOCKED) != 0)
                flags.setUserLock(false);

            if ((saveFlags & SAVE_CHOWN) != 0 && me != null) {
                if (entity instanceof CEntity<?>) {
                    CEntity<?> ce = (CEntity<?>) entity;
                    ce.setOwner(me);
                }
            }
        }

        try {
            serviceFor(entityClass).saveOrUpdateAll(entities);
        } catch (Exception e) {
            uiLogger.error("保存失败", e);
            return;
        }

        uiLogger.info("保存成功");
        showIndex();
    }

    @Operation
    public void deleteSelection() {
        deleteSelection(deleteFlags);
    }

    protected void deleteSelection(int deleteFlags) {
        checkDeleteFlags(deleteFlags);

        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }

        Collection<? extends Entity<?>> entities;
        try {
            entities = unmarshalDtos(getSelection(), true);
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : entities) {
            boolean needUpdateBeforeDelete = false;

            // force to delete locked
            if ((deleteFlags & DELETE_FORCE) != 0) {
                EntityFlags flags = EntityAccessor.getFlags(entity);
                if (flags.isUserLock()) {
                    flags.setUserLock(false);
                    // save & reload it before delete..?
                    needUpdateBeforeDelete = true;
                }
            }

            if ((deleteFlags & DELETE_DETACH) != 0) {
                entity.detach();
                needUpdateBeforeDelete = true; // optim: only detach changes something..?
            }

            if (needUpdateBeforeDelete) {
                serviceFor(entityClass).update(entity);
            }

            boolean locked = EntityAccessor.isLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }
        }

        if (!lockedList.isEmpty()) {
            StringBuilder buf = new StringBuilder();
            buf.append("以下对象被锁定：\n");
            for (Entity<?> lockedEntity : lockedList) {
                String entryText = lockedEntity.getEntryText();
                buf.append("    " + entryText + ", \n");
            }
            uiLogger.error("不能删除锁定的对象: " + buf.toString());
            return;
        }

        setSelection(null);

        try {
            serviceFor(entityClass).deleteAll(entities);
        } catch (Exception e) {
            uiLogger.error("删除失败", e);
            return;
        }

        refreshCount();
    }

    void openSelectedDtos(int reloadDtoSel) {
        List<Object> reloadedList = new ArrayList<Object>();
        for (Object selection : getSelection()) {
            Object reloaded;
            if (selection instanceof EntityDto<?, ?>) {
                EntityDto<?, ?> dto = (EntityDto<?, ?>) selection;
                reloaded = reload(dto, reloadDtoSel);
            } else
                reloaded = selection;
            reloadedList.add(reloaded);
        }
        setActiveObjects(reloadedList);
    }

    Collection<? extends Entity<?>> unmarshalDtos(Collection<?> objects, boolean nullable) {
        Set<Entity<?>> entities = new HashSet<Entity<?>>();
        for (Object object : objects) {
            if (dtoClass.isInstance(object)) {
                EntityDto<?, ?> entityDto = (EntityDto<?, ?>) object;
                Entity<?> entity = entityDto.unmarshal(this);
                if (entity == null) {
                    if (!nullable)
                        throw new NullPointerException("DTO unmarshalled to null: " + entityDto);
                    else
                        continue;
                }
                entities.add(entity);
            }
        }
        return entities;
    }

    static void checkSaveFlags(int saveFlags) {
        int forceLockedMask = SAVE_FORCE | SAVE_LOCKED;
        if ((saveFlags & forceLockedMask) == forceLockedMask)
            throw new IllegalUsageException("SAVE_FORCE and SAVE_LOCKED are exclusive.");
    }

    static void checkDeleteFlags(int deleteFlags) {
    }

}
