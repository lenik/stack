package com.bee32.plover.orm.ext.userCategory;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class UserCategoryItemDto
        extends EntityDto<UserCategoryItem, Long> {

    private static final long serialVersionUID = 1L;

    UserCategoryDto category;

    Long intVal;
    Double floatVal;
    String textVal;

    public UserCategoryItemDto() {
        super();
    }

    public UserCategoryItemDto(UserCategoryItem source) {
        super(source);
    }

    @Override
    protected void _marshal(UserCategoryItem source) {
        category = new UserCategoryDto(source.getCategory());
        intVal = source.intVal;
        floatVal = source.floatVal;
        textVal = source.textVal;
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, UserCategoryItem target) {
        with(context, target).unmarshal("category", category);
        target.intVal = intVal;
        target.floatVal = floatVal;
        target.textVal = textVal;
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category");
        category = new UserCategoryDto().ref(categoryId);

        char categoryType = map.getChar("category.type");
        switch (categoryType) {
        case UserCategory.INTEGER:
            intVal = map.getLong("value");
            break;

        case UserCategory.DECIMAL:
            floatVal = map.getDouble("value");
            break;

        case UserCategory.TEXT:
            textVal = map.getString("value");
            break;

        default:
            throw new ParseException("Bad category type: " + categoryType);
        }
    }

}
