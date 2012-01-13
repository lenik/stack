package com.bee32.icsf.access;

import java.io.Serializable;
import java.util.Arrays;

public final class Permission
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private int allowBits;
    private int denyBits;

    public static final int ADMIN = 1 << 31;
    static final char C_ADMIN = 'S';

    public static final int LIST = 1 << 5;
    public static final int CREATE = 1 << 4; // CREATE-USER on user repository
    public static final int DELETE = 1 << 3; // DELETE-USER on user repository
    static final char C_LIST = 'l';
    static final char C_CREATE = 'c';
    static final char C_DELETE = 'd';

    public static final int READ = 1 << 2;
    public static final int WRITE = 1 << 1;
    public static final int EXECUTE = 1 << 0;
    static final char C_READ = 'r';
    static final char C_WRITE = 'w';
    static final char C_EXECUTE = 'x';

    public Permission(int allowBits) {
        this.allowBits = allowBits;
    }

    public Permission(int allowBits, int denyBits) {
        this.allowBits = allowBits;
        this.denyBits = denyBits;
    }

    public Permission(String mode) {
        String allow;
        String deny;

        int excl = mode.indexOf('!');
        if (excl == -1) {
            allow = mode;
            deny = null;
        } else {
            allow = mode.substring(0, excl);
            deny = mode.substring(excl + 1);
        }

        this.allowBits = parseBits(allow);
        if (deny != null)
            this.denyBits = parseBits(deny);
    }

    @Override
    public Permission clone() {
        return new Permission(allowBits, denyBits);
    }

    public int getAllowBits() {
        return allowBits;
    }

    public void setAllowBits(int allowBits) {
        this.allowBits = allowBits;
    }

    public int getDenyBits() {
        return denyBits;
    }

    public void setDenyBits(int denyBits) {
        this.denyBits = denyBits;
    }

    public void clear(int bits) {
        this.allowBits &= ~bits;
        this.denyBits &= ~bits;
    }

    public void set(Boolean f, int bits) {
        clear(bits);
        if (f == null)
            return;
        if (f)
            this.allowBits |= bits;
        else
            this.denyBits |= bits;
    }

    public boolean test(int bits) {
        if ((denyBits & bits) != 0)
            return false;
        if ((allowBits & bits) == bits)
            return true;
        // deny by default.
        return false;
    }

    public Boolean test3(int bits) {
        if ((denyBits & bits) != 0)
            return false;
        if ((allowBits & bits) == bits)
            return true;
        return null;
    }

    public void allow(int bits) {
        clear(bits);
        this.allowBits |= bits;
    }

    public void allow(boolean f, int bits) {
        clear(bits);
        if (f)
            this.allowBits |= bits;
    }

    public void deny(int bits) {
        clear(bits);
        this.denyBits |= bits;
    }

    public void deny(boolean f, int bits) {
        clear(bits);
        if (f)
            this.denyBits |= bits;
    }

    public boolean implies(int bits) {
        int m = bits & ~denyBits & allowBits;
        return m == bits;
    }

    public boolean implies(String mode) {
        if (mode == null)
            throw new NullPointerException("mode");
        Permission o = new Permission(mode);
        return implies(o);
    }

    public boolean implies(Permission other) {
        if (other == null)
            throw new NullPointerException("other");
        int effectiveBits = other.allowBits & ~other.denyBits;
        return implies(effectiveBits);
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
            this.allowBits |= permission.allowBits;
    }

    public Boolean getAdmin() {
        return test3(ADMIN);
    }

    public void setAdmin(Boolean f) {
        set(f, ADMIN);
    }

    public Boolean getReadable() {
        return test3(READ);
    }

    public Boolean getWritable() {
        return test3(WRITE);
    }

    public Boolean getExecutable() {
        return test3(EXECUTE);
    }

    public Boolean getListable() {
        return test3(LIST);
    }

    public Boolean getCreatable() {
        return test3(CREATE);
    }

    public Boolean getDeletable() {
        return test3(DELETE);
    }

    public void setReadable(Boolean f) {
        set(f, READ);
    }

    public void setWritable(Boolean f) {
        set(f, WRITE);
    }

    public void setExecutable(Boolean f) {
        set(f, EXECUTE);
    }

    public void setListable(Boolean f) {
        set(f, LIST);
    }

    public void setCreatable(Boolean f) {
        set(f, CREATE);
    }

    public void setDeletable(Boolean f) {
        set(f, DELETE);
    }

    public boolean canRead() {
        return test(READ);
    }

    public boolean canWrite() {
        return test(WRITE);
    }

    public boolean canExecute() {
        return test(EXECUTE);
    }

    public boolean canList() {
        return test(LIST);
    }

    public boolean canCreate() {
        return test(CREATE);
    }

    public boolean canDelete() {
        return test(DELETE);
    }

    public void canRead(boolean f) {
        allow(f, READ);
    }

    public void canWrite(boolean f) {
        allow(f, WRITE);
    }

    public void canExecute(boolean f) {
        allow(f, EXECUTE);
    }

    public void canList(boolean f) {
        allow(f, LIST);
    }

    public void canCreate(boolean f) {
        allow(f, CREATE);
    }

    public void canDelete(boolean f) {
        allow(f, DELETE);
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

    static void formatBits(StringBuilder sb, int bits) {
        for (int index = 31; index >= 0; index--) {
            if (bits < 0) {
                char ch = i2c[index];
                if (ch != '\0')
                    sb.append(ch);
            }
            bits <<= 1;
        }
    }

    public String getModeString() {
        StringBuilder sb = new StringBuilder(32);
        formatBits(sb, allowBits);

        if (denyBits != 0) {
            sb.append('!');
            formatBits(sb, denyBits);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getModeString();
    }

    @Override
    public int hashCode() {
        return allowBits * 17 + denyBits;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Permission))
            return false;
        Permission o = (Permission) obj;
        if (allowBits != o.allowBits)
            return false;
        if (denyBits != o.denyBits)
            return false;
        return true;
    }

    public long toLong() {
        long longValue = ((denyBits & 0xFFFFFFFFL) << 32) | (allowBits & 0xFFFFFFFFL);
        return longValue;
    }

    public static Permission fromLong(long longValue) {
        int hi_deny = (int) (longValue >> 32);
        int lo_allow = (int) (longValue);
        return new Permission(lo_allow, hi_deny);
    }

}
