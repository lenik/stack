package com.bee32.plover.ox1.dict;

public class DtoCodeMapper
        implements IKeyMapper<NameDictDto<?>, String> {

    @Override
    public String getKey(NameDictDto<?> obj) {
        String name = obj.getName();
        return name;
    }

    public static final DtoCodeMapper INSTANCE = new DtoCodeMapper();

}
