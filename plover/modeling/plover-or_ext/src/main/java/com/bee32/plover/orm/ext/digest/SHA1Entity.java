package com.bee32.plover.orm.ext.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 0e72f635 40ab3fc5 fcb42357 17e8fabe 4a9786ee => 160-bit, or 40 hex digits
 */
@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = 40)) })
public abstract class SHA1Entity
        extends DigestEntity {

    private static final long serialVersionUID = 1L;

    @Override
    protected MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("SHA1 isn't supported", e);
        }
    }

}
