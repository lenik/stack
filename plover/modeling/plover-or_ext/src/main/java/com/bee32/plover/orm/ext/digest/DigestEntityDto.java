package com.bee32.plover.orm.ext.digest;

import com.bee32.plover.orm.ext.color.UIEntityDto;

public abstract class DigestEntityDto<E extends DigestEntity>
        extends UIEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    String digest;

    public DigestEntityDto() {
        super();
    }

    public DigestEntityDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        digest = source.getDigest();
    }

    public String getDigest() {
        return digest;
    }

}
