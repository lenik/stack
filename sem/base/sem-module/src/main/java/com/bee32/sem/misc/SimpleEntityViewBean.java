package com.bee32.sem.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.primefaces.model.LazyDataModel;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public abstract class SimpleEntityViewBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected Class<? extends Entity<?>> entityClass;
    protected Class<? extends EntityDto<?, ?>> dtoClass;
    protected ICriteriaElement[] criteriaElements;
    private LazyDataModel<?> dataModel;

    public <E extends Entity<?>, D extends EntityDto<? super E, ?>> //
    /*    */SimpleEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection,
            ICriteriaElement... criteriaElements) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.criteriaElements = criteriaElements;
        EntityDataModelOptions<E, D> options = new EntityDataModelOptions<E, D>(//
                entityClass, dtoClass, selection, getCriteriaElements());
        this.dataModel = UIHelper.buildLazyDataModel(options);
        refreshCount();
    }

    protected void refreshCount() {
        int count = serviceFor(entityClass).count(getCriteriaElements());
        dataModel.setRowCount(count);
    }

    public LazyDataModel<?> getDataModel() {
        return dataModel;
    }

    protected ICriteriaElement[] getCriteriaElements() {
        return criteriaElements;
    }

    protected void setCriteriaElements(ICriteriaElement... criteriaElements) {
        this.criteriaElements = criteriaElements;
    }

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
            EntityFlags flags = EntityAccessor.getFlags(entity);
            if (flags.isLocked() || flags.isUserLock())
                lockedList.add(entity);
            // entity.detach();
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

}
