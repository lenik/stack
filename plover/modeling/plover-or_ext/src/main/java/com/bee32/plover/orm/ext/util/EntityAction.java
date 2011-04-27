package com.bee32.plover.orm.ext.util;

public class EntityAction {

    private final EntityActionType type;

    public EntityAction(EntityActionType type) {
        if (type == null)
            throw new NullPointerException("type");

        this.type = type;
    }

    public EntityActionType getType() {
        return type;
    }

    public static EntityAction CREATE = new EntityAction(EntityActionType.CREATE);
    public static EntityAction LOAD = new EntityAction(EntityActionType.LOAD);
    public static EntityAction SAVE = new EntityAction(EntityActionType.SAVE);
    public static EntityAction POST_SAVE = new EntityAction(EntityActionType.POST_SAVE);

}
