package com.bee32.sem.misc;

import java.util.List;

import com.bee32.plover.arch.util.IObjectOpenListener;
import com.bee32.plover.arch.util.IPriority;
import com.bee32.plover.arch.util.ISelectionChangeListener;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.EntityDto;

public abstract class SevbFriend
        extends DataViewBean
        implements IPriority, ISelectionChangeListener, IObjectOpenListener {

    private static final long serialVersionUID = 1L;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void selectionChanged(List<?> selections) {
        if (selections.isEmpty())
            unselect();
        else {
            Object first = selections.get(0);
            select(first);
        }
    }

    protected abstract void select(Object mainEntry);

    protected void unselect() {
        setSelection(null);
    }

    @Override
    public void objectOpened(List<?> openedObjects) {
        if (openedObjects.isEmpty())
            close();
        else {
            Object first = openedObjects.get(0);
            open(first);
        }
    }

    protected abstract void open(Object mainOpenedObject);

    protected void close() {
        setOpenedObject(null);
    }

    public boolean isRequired() {
        return false;
    }

    /**
     * 一般用于，在用对话框挑选本友元时，返回的是 DTO-引用，通过 target="#{bean.friend.reload}" 使重新装载 DTO。
     *
     * 如果本友元是必选项（ friend.required），传入的 null 将被忽略。
     */
    public void setReload(EntityDto<?, ?> dto) {
        if (dto == null) {
            if (isRequired()) {
                // uiLogger.warn("Skipped null selection.");
                return;
            } else {
                close();
                setOpenedObject(null);
            }
        } else {
            EntityDto<?, ?> reloaded = reload(dto, -1);
            setOpenedObject(reloaded);
        }
    }

    public boolean preUpdate(UnmarshalMap uMap) {
        return true;
    }

    public void postUpdate(UnmarshalMap uMap) {
    }

    public boolean preDelete(UnmarshalMap uMap) {
        return true;
    }

    public void postDelete(UnmarshalMap uMap) {
    }

    @Deprecated
    public boolean isOverrider() {
        return false;
    }

    public abstract void saveOpenedObject(int saveFlags, UnmarshalMap _uMap);

    public abstract void deleteSelection(int deleteFlags);

}
