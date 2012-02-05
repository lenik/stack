package com.bee32.plover.ox1.digest;

import com.bee32.plover.ox1.color.UIEntityDto;

public abstract class DigestEntityDto<E extends DigestEntity>
        extends UIEntityDto<E, String> {

    private static final long serialVersionUID = 1L;

    String digest;

    public DigestEntityDto() {
        super();
    }

    public DigestEntityDto(int fmask) {
        super(fmask);
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
