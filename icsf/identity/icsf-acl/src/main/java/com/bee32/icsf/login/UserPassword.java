package com.bee32.icsf.login;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Blue;

@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "user_password_seq", allocationSize = 1)
public class UserPassword
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int EXPIRE_HOURS = 240;

    static Random random = new Random();

    User user;

    int salt = random.nextInt();

    String master;
    String passwd; // Not used.

    PrivateQuestion resetQ = predefined(PrivateQuestions.class).DADS_NAME;
    String resetA = "";
    int resetTicket;
    Date resetExpires = new Date();

    public UserPassword() {
    }

    public UserPassword(User user, String passwd) {
        this(user, passwd, null);
    }

    public UserPassword(User user, String passwd, String master) {
        if (user == null)
            throw new NullPointerException("user");
        if (passwd == null)
            throw new NullPointerException("passwd");
        this.user = user;
        this.passwd = passwd;
        this.master = master;
    }

    @OneToOne
    @JoinColumn(nullable = false)
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

    @Column(length = 40)
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Column(length = 40)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public static String digest(String text) {
        return DigestUtils.shaHex(text);
    }

}
