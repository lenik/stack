package com.bee32.icsf.login;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

@Entity
@Blue
public class UserPassword
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int EXPIRE_HOURS = 240;

    static Random random = new Random();

    User user;

    int salt = random.nextInt();

    String master = "";
    String masterMd5;

    String passwd = ""; // Not used.
    String passwdMd5;

    PrivateQuestion resetQ = PrivateQuestion.DADS_NAME;
    String resetA = "";
    int resetTicket;
    Date resetExpires = new Date();

    public UserPassword() {
    }

    @ManyToOne
    @Column(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(length = 10, nullable = false)
    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }

    @Column(length = 30)
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        if (master == null)
            throw new NullPointerException("master");
        this.master = master;
        this.masterMd5 = MD5Util.md5(master);
    }

    @Column(length = 32)
    public String getMasterMd5() {
        return masterMd5;
    }

    public void setMasterMd5(String masterMd5) {
        this.masterMd5 = masterMd5;
    }

    @Column(length = 30)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        if (passwd == null)
            throw new NullPointerException("passwd");
        this.passwd = passwd;
        this.passwdMd5 = MD5Util.md5(passwd);
    }

    @Column(length = 32)
    public String getPasswdMd5() {
        return passwdMd5;
    }

    public void setPasswdMd5(String passwdMd5) {
        this.passwdMd5 = passwdMd5;
    }

    public void createResetTicket() {
        resetTicket = random.nextInt();
        long expires = System.currentTimeMillis() + EXPIRE_HOURS * 3600L * 1000000L;
        resetExpires = new Date(expires);
    }

    public boolean validateResetTicket() {
        if (resetExpires == null)
            return false;

        // now < resetExpires
        int cmp = new Date().compareTo(resetExpires);
        if (cmp > 0) // expired.
            return false;

        return true;
    }

    @ManyToOne
    public PrivateQuestion getResetQ() {
        return resetQ;
    }

    public void setResetQ(PrivateQuestion resetQ) {
        this.resetQ = resetQ;
    }

    @Column(length = 30, nullable = false)
    public String getResetA() {
        return resetA;
    }

    public void setResetA(String resetA) {
        this.resetA = resetA;
    }

    @Column(nullable = false)
    public int getResetTicket() {
        return resetTicket;
    }

    public void setResetTicket(int resetTicket) {
        this.resetTicket = resetTicket;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getResetExpires() {
        return resetExpires;
    }

    public void setResetExpires(Date resetExpires) {
        this.resetExpires = resetExpires;
    }

    @Transient
    public int getResetExpiresHours() {
        if (resetExpires == null)
            return 0;
        long diffSeconds = (resetExpires.getTime() - System.currentTimeMillis()) / 1000000;
        long hours = diffSeconds / 3600;
        return (int) hours;
    }

}
