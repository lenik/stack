package com.bee32.plover.ox1.dict;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.util.TextUtil;

public abstract class NameDictDto<E extends NameDict>
        extends DictEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    int order;
    float rank;

    public NameDictDto() {
        super();
    }

    public NameDictDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        order = source.getOrder();
        rank = source.getRank();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        target.setOrder(order);
        target.setRank(rank);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);

        // name will always overwrite the id here.
        setName(map.getString("name"));
        setOrder(map.getInt("order"));
        setRank(map.getFloat("rank"));
    }

    public String getName() {
        return getId();
    }

    public void setName(String name) {
        name = TextUtil.normalizeSpace(name);
        setId(name);
    }

    public String getDisplayId() {
        if (id == null)
            return "";
        else
            return id;
    }

    public void setDisplayId(String displayId) {
        if (displayId != null && displayId.isEmpty())
            id = null;
        else
            id = displayId;
    }

    // Using the default id-equality.
    //
    // protected Boolean naturalEquals(EntityDto<E, String> other);
    // protected Integer naturalHashCode();

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

}
