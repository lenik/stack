package com.bee32.plover.ox1.dict;

import com.bee32.plover.orm.entity.Entity;

public class NameMapper
        implements IKeyMapper<Entity<String>, String> {

    @Override
    public String getKey(Entity<String> obj) {
        String name = obj.getName();
        return name;
    }

    public static final NameMapper INSTANCE = new NameMapper();

}
