package com.bee32.icsf.principal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class UserEmail
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    public static final char EMAIL_INIT = '?';
    public static final char EMAIL_VERIFYING = '1';
    public static final char EMAIL_VERIFIED = 'V';

    Principal principal;

    String address;

    char status = EMAIL_INIT;

    public UserEmail() {
    }

    public UserEmail(Principal principal, String address) {
        this.principal = principal;
        this.address = address;
    }

    @Column(length = 40)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    public char getEmailStatus() {
        return status;
    }

    public void setEmailStatus(char emailStatus) {
        this.status = emailStatus;
    }

    @Transient
    public boolean isEmailVerified() {
        return status == EMAIL_VERIFIED;
    }

}
