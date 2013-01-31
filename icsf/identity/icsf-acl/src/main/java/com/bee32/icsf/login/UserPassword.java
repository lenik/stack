package com.bee32.icsf.login;

import java.nio.charset.Charset;
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

import org.apache.commons.codec.binary.Base64;
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

    public static final int MASTER_LENGTH = 40;
    public static final int PASSWD_LENGTH = 40;

    public static final int EXPIRE_HOURS = 240;

    static Random random = new Random();

    User user;

    int salt = random.nextInt();
    String master = "";
    String passwd = "";

    PrivateQuestion resetQ;
    String resetA = "";
    int resetTicket;
    Date resetExpires = new Date();

    public UserPassword() {
    }

    public UserPassword(User user, String passwd) {
        this(user, passwd, null);
    }

    public UserPassword(User user, String passwd, String master) {
        init(user, passwd, master);
    }

    public void init(User user, String passwd) {
        init(user, passwd, null);
    }

    public void init(User user, String passwd, String master) {
        if (user == null)
            throw new NullPointerException("user");
        if (passwd == null)
            throw new NullPointerException("passwd");
        this.user = user;
        this.passwd = passwd;
        this.master = master;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof UserPassword)
            _populate((UserPassword) source);
        else
            super.populate(source);
    }

    protected void _populate(UserPassword o) {
        super._populate(o);
        user = o.user;
        salt = o.salt;
        master = o.master;
        passwd = o.passwd;
        resetQ = o.resetQ;
        resetA = o.resetA;
        resetTicket = o.resetTicket;
        resetExpires = o.resetExpires;
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

    @Transient
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        if (master == null)
            throw new NullPointerException("master");
        this.master = master;
    }

    @Column(name = "master", length = MASTER_LENGTH)
    public String get_master() {
        return encrypt(master);
    }

    public void set_master(String master) {
        this.master = decrypt(master);
    }

    @Transient
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        if (passwd == null)
            throw new NullPointerException("passwd");
        this.passwd = passwd;
    }

    @Column(name = "passwd", length = PASSWD_LENGTH)
    String get_passwd() {
        return encrypt(passwd);
    }

    void set_passwd(String passwd) {
        if (passwd == null)
            throw new NullPointerException("passwd");
        this.passwd = decrypt(passwd);
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

    static final Charset utf8Charset = Charset.forName("utf-8");

    public static String encrypt(String text) {
        if (text == null)
            return null;
        if (text.length() > 12)
            text = text.substring(0, 12);
        byte[] textBytes = text.getBytes(utf8Charset);
        byte[] base64Bytes = Base64.encodeBase64(textBytes);
        String base64 = new String(base64Bytes, utf8Charset);
        return base64;
    }

    public static String decrypt(String base64) {
        if (base64 == null)
            return null;
        byte[] base64Bytes = base64.getBytes(utf8Charset);
        byte[] textBytes = Base64.decodeBase64(base64Bytes);
        String text = new String(textBytes, utf8Charset);
        return text;
    }

    public static void main(String[] args) {
        String[] commonPasswords = { "", "Bee32", "guest", "secret", "168168", "000000", "123123", "888888",
                "13666670345", "hmzy888888", "15967301888", "fya1888888", "278591" };
        for (String password : commonPasswords) {
            String sha = DigestUtils.shaHex(password);
            System.out.println("-- Rewrite password \"" + password + "\"");
            System.out.println("update user_password set passwd='" + encrypt(password) //
                    + "' where passwd='" + sha + "';");
        }
    }

}
