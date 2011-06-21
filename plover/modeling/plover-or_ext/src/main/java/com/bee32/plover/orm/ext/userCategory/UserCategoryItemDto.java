package com.bee32.plover.orm.ext.userCategory;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class UserCategoryItemDto
        extends EntityDto<UserCategoryItem, Long> {

    private static final long serialVersionUID = 1L;

    UserCategoryDto category;

    Long intVal;
    Double floatVal;
    String textVal;

    @Override
    protected void _marshal(UserCategoryItem source) {
        category = mref(UserCategoryDto.class, source.getCategory());
        intVal = source.intVal;
        floatVal = source.floatVal;
        textVal = source.textVal;
    }

    @Override
    protected void _unmarshalTo(UserCategoryItem target) {
        merge(target, "category", category);
        target.intVal = intVal;
        target.floatVal = floatVal;
        target.textVal = textVal;
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category");
        category = new UserCategoryDto().ref(categoryId);

        String _categoryType = map.getString("category.type");
        UserCategoryTypeEnum categoryType = UserCategoryTypeEnum.valueOf(_categoryType);
        switch (categoryType) {
        case INTEGER:
            intVal = map.getLong("value");
            break;

        case DECIMAL:
            floatVal = map.getDouble("value");
            break;

        case TEXT:
            textVal = map.getString("value");
            break;

        default:
            throw new ParseException("Bad category type: " + categoryType);
        }
    }

}
