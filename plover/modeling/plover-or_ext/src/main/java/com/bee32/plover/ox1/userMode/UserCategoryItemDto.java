package com.bee32.plover.ox1.userMode;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;

public class UserCategoryItemDto
        extends CEntityDto<UserCategoryItem, Long> {

    private static final long serialVersionUID = 1L;

    UserCategoryDto category;

    Long intVal;
    Double doubleVal;
    String textVal;

    @Override
    protected void _marshal(UserCategoryItem source) {
        category = mref(UserCategoryDto.class, source.getCategory());
        intVal = source.intVal;
        doubleVal = source.doubleVal;
        textVal = source.textVal;
    }

    @Override
    protected void _unmarshalTo(UserCategoryItem target) {
        merge(target, "category", category);
        target.intVal = intVal;
        target.doubleVal = doubleVal;
        target.textVal = textVal;
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category");
        category = new UserCategoryDto().ref(categoryId);

        char _categoryType = map.getChar("category.type");

        switch (_categoryType) {
        case UserDataType.T_INT:
            intVal = map.getLong("value");
            break;

        case UserDataType.T_DOUBLE:
            doubleVal = map.getDouble("value");
            break;

        case UserDataType.T_TEXT:
            textVal = map.getString("value");
            break;

        default:
            throw new ParseException("Bad category type: " + _categoryType);
        }
    }

}
