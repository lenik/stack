package com.bee32.icsf.access;

import java.io.Serializable;
import java.util.Arrays;

public final class Permission
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private int bits;

    public static final int READ = 1 << 2;
    public static final int WRITE = 1 << 1;
    public static final int EXECUTE = 1 << 0;
    static final char C_READ = 'r';
    static final char C_WRITE = 'w';
    static final char C_EXECUTE = 'x';

    public static final int LIST = 1 << 5;
    public static final int CREATE = 1 << 4; // CREATE-USER on user repository
    public static final int DELETE = 1 << 3; // DELETE-USER on user repository
    static final char C_LIST = 'l';
    static final char C_CREATE = 'c';
    static final char C_DELETE = 'd';

    public static final int ADMIN = 1 << 31;
    static final char C_ADMIN = 'S';

    public Permission(int bits) {
        this.bits = bits;
    }

    public Permission(String mode) {
        this.bits = parseBits(mode);
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public void addBits(int bits) {
        this.bits |= bits;
    }

    public void removeBits(int bits) {
        this.bits &= ~bits;
    }

    public void setBits(boolean f, int bits) {
        if (f)
            this.bits |= bits;
        else
            this.bits &= ~bits;
    }

    public boolean implies(int bits) {
        int and = this.bits & bits;
        return and == bits;
    }

    public boolean implies(String mode) {
        if (mode == null)
            throw new NullPointerException("mode");
        int modeBits = parseBits(mode);
        return implies(modeBits);
    }

    public boolean implies(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        return implies(permission.bits);
    }

    /**
     * Merge the given permission to this permission, so this permission will contain all the
     * allowed bits of the given permission.
     *
     * @param permission
     *            The permission to merge. Skipped if <code>null</code>.
     */
    public void merge(Permission permission) {
        if (permission != null)
            this.bits |= permission.bits;
    }

    public boolean canRead() {
        return (bits & READ) != 0;
    }

    public boolean canWrite() {
        return (bits & WRITE) != 0;
    }

    public boolean canExecute() {
        return (bits & EXECUTE) != 0;
    }

    public boolean canList() {
        return (bits & LIST) != 0;
    }

    public boolean canCreate() {
        return (bits & CREATE) != 0;
    }

    public boolean canDelete() {
        return (bits & DELETE) != 0;
    }

    public boolean isAdmin() {
        return (bits & ADMIN) != 0;
    }

    public void canRead(boolean f) {
        setBits(f, READ);
    }

    public void canWrite(boolean f) {
        setBits(f, WRITE);
    }

    public void canExecute(boolean f) {
        setBits(f, EXECUTE);
    }

    public void canList(boolean f) {
        setBits(f, LIST);
    }

    public void canCreate(boolean f) {
        setBits(f, CREATE);
    }

    public void canDelete(boolean f) {
        setBits(f, DELETE);
    }

    public void setAdmin(boolean f) {
        setBits(f, ADMIN);
    }

    static final char[] i2c;
    static final int[] c2m;
    static {
        i2c = new char[32];
        i2c[m2i(READ)] = C_READ;
        i2c[m2i(WRITE)] = C_WRITE;
        i2c[m2i(EXECUTE)] = C_EXECUTE;
        i2c[m2i(LIST)] = C_LIST;
        i2c[m2i(CREATE)] = C_CREATE;
        i2c[m2i(DELETE)] = C_DELETE;
        i2c[m2i(ADMIN)] = C_ADMIN;

        c2m = new int[128];
        Arrays.fill(c2m, -1);

        int mask = 1;
        for (int index = 0; index < i2c.length; index++) {
            char ch = i2c[index];
            c2m[ch] = mask;
            mask <<= 1;
        }
    }

    static int m2i(int mask) {
        int i = -1;
        while (mask != 0) {
            i++;
            mask >>>= 1;
        }
        return i;
    }

    static int parseBits(CharSequence seq) {
        int bits = 0;
        int len = seq.length();
        char ch = '?';
        try {
            for (int i = 0; i < len; i++) {
                ch = seq.charAt(i);
                int bit = c2m[ch];
                if (bit == -1)
                    throw new IllegalArgumentException("Bad permission character: " + ch);
                bits |= bit;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Bad permission character: " + ch);
        }
        return bits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        int bits = this.bits;
        for (int index = 31; index >= 0; index--) {
            if (bits < 0) {
                char ch = i2c[index];
                if (ch != '\0')
                    sb.append(ch);
            }

            bits <<= 1;
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return bits;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Permission))
            return false;
        Permission o = (Permission) obj;
        return bits == o.bits;
    }

}
