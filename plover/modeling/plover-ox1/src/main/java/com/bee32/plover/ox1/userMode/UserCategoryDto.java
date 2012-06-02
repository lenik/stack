package com.bee32.plover.ox1.userMode;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.NameDictDto;

public class UserCategoryDto
        extends NameDictDto<UserCategory> {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    UserDataType type;
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

        char _type = map.getChar("type");
        type = UserDataType.forValue(_type);

        precision = map.getInt("precision");
        scale = map.getInt("scale");

        if (selection.contains(ITEMS)) {
            items = new ArrayList<UserCategoryItemDto>();

            String[] _items = map.getStringArray("item");
            if (_items != null)
                for (String _item : _items) {
                    UserCategoryItemDto item = new UserCategoryItemDto();
                    // XXX Should parse the full item attributes, not just ref.
                    item = item.parseRef(_item);
                    items.add(item);
                }
        }
    }

    public UserDataType getType() {
        return type;
    }

    public void setType(UserDataType type) {
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
