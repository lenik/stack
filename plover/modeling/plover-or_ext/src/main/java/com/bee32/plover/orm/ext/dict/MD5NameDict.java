package com.bee32.plover.orm.ext.dict;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 3cc5aac1 2122ef7b d4943ac4 850a923a => 128-bit, or 32 hex digits.
 */
@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = 32)) })
public abstract class MD5NameDict
        extends DigestNameDict {

    private static final long serialVersionUID = 1L;

    @Override
    protected MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("MD5 isn't supported", e);
        }
    }

}
