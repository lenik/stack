package com.bee32.plover.orm.ext.dict;

public abstract class DigestNameDictDto<E extends DigestNameDict>
        extends NameDictDto<E> {

    private static final long serialVersionUID = 1L;

    String digest;

    public DigestNameDictDto() {
        super();
    }

    public DigestNameDictDto(E source) {
        super(source);
    }

    public DigestNameDictDto(int selection) {
        super(selection);
    }

    public DigestNameDictDto(int selection, E source) {
        super(selection, source);
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
