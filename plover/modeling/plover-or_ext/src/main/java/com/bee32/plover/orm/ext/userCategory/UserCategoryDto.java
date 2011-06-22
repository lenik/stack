package com.bee32.plover.orm.ext.userCategory;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.NameDictDto;

public class UserCategoryDto
        extends NameDictDto<UserCategory> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    UserCategoryTypeEnum type;
    int precision;
    int scale;

    List<UserCategoryItemDto> items;

    public UserCategoryDto() {
        super();
    }

    @Override
    protected void _marshal(UserCategory source) {
        type = source.getType();
        precision = source.getPrecision();
        scale = source.getScale();

        if (selection.contains(ITEMS))
            items = marshalList(UserCategoryItemDto.class, source.getItems());
    }

    @Override
    protected void _unmarshalTo(UserCategory target) {
        target.setType(type);
        target.setPrecision(precision);
        target.setScale(scale);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {

        String _type = map.getString("type");
        type = UserCategoryTypeEnum.valueOf(_type);

        precision = map.getInt("precision");
        scale = map.getInt("scale");

        if (selection.contains(ITEMS)) {
            items = new ArrayList<UserCategoryItemDto>();

            String[] _items = map.getStringArray("item");
            if (_items != null)
                for (String _item : _items) {
                    // TODO ...
                }
        }
    }

    public UserCategoryTypeEnum getType() {
        return type;
    }

    public void setType(UserCategoryTypeEnum type) {
        this.type = type;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public List<UserCategoryItemDto> getItems() {
        return items;
    }

    public void setItems(List<UserCategoryItemDto> items) {
        this.items = items;
    }

}
