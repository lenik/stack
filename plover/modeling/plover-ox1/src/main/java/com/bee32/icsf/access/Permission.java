package com.bee32.icsf.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Permission
        implements Serializable {

    private static final long serialVersionUID = 1L;

    int allowBits;
    int denyBits;

    public static final char modeSeparator = '/';

    public static final int OWN = 1 << 31;
    public static final int USE = 1 << 30;
    public static final int CREATE = 1 << 4; // CREATE-USER on user repository
    public static final int DELETE = 1 << 3; // DELETE-USER on user repository
    public static final int READ = 1 << 2;
    public static final int WRITE = 1 << 1;
    public static final int EXECUTE = 1 << 0;

    public static final int R_X = USE | READ | EXECUTE;
    public static final int RWX = USE | READ | EXECUTE | WRITE | CREATE | DELETE;
    public static final int RWS = USE | READ | EXECUTE | WRITE | CREATE | DELETE | OWN;

    static final char C_ADMIN = 's';
    static final char C_USER = 'u';
    static final char C_CREATE = 'c';
    static final char C_DELETE = 'd';
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

        int excl = mode.indexOf(modeSeparator);
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

    public boolean test(int bits) {
        if ((denyBits & bits) != 0)
            return false;
        if ((allowBits & bits) == bits)
            return true;
        // allow by default.
        return true;
    }

    public void set(boolean f, int bits) {
        if (f) {
            allowBits |= bits;
            denyBits &= ~bits;
        } else {
            allowBits &= ~bits;
            denyBits |= bits;
        }
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
     * @param o
     *            The permission to merge. Skipped if <code>null</code>.
     */
    public void merge(Permission o) {
        if (o != null) {
            this.allowBits |= o.allowBits;
            this.denyBits |= o.denyBits;
            allowBits &= ~denyBits;
        }
    }

    public Permission add(Permission o) {
        if (o == null)
            return clone();
        int allowBits = this.allowBits | o.allowBits;
        int denyBits = this.denyBits | o.denyBits;
        allowBits &= ~denyBits;
        return new Permission(allowBits, denyBits);
    }

    public boolean isAdmin() {
        return test(OWN);
    }

    public boolean isUsable() {
        return test(USE);
    }

    public boolean isReadable() {
        return test(READ);
    }

    public boolean isWritable() {
        return test(WRITE);
    }

    public boolean isExecutable() {
        return test(EXECUTE);
    }

    public boolean isCreatable() {
        return test(CREATE);
    }

    public boolean isDeletable() {
        return test(DELETE);
    }

    public void setAdmin(boolean f) {
        set(f, OWN);
    }

    public void setUsable(boolean f) {
        set(f, USE);
    }

    public void setReadable(boolean f) {
        set(f, READ);
    }

    public void setWritable(boolean f) {
        set(f, WRITE);
    }

    public void setExecutable(boolean f) {
        set(f, EXECUTE);
    }

    public void setCreatable(boolean f) {
        set(f, CREATE);
    }

    public void setDeletable(boolean f) {
        set(f, DELETE);
    }

    public SinglePermissionBit getOwn() {
        return new SinglePermissionBit(this, OWN);
    }

    public SinglePermissionBit getUse() {
        return new SinglePermissionBit(this, USE);
    }

    public SinglePermissionBit getRead() {
        return new SinglePermissionBit(this, READ);
    }

    public SinglePermissionBit getWrite() {
        return new SinglePermissionBit(this, WRITE);
    }

    public SinglePermissionBit getExecute() {
        return new SinglePermissionBit(this, EXECUTE);
    }

    public SinglePermissionBit getCreate() {
        return new SinglePermissionBit(this, CREATE);
    }

    public SinglePermissionBit getDelete() {
        return new SinglePermissionBit(this, DELETE);
    }

    static final char[] i2c;
    static final int[] c2m;
    static {
        i2c = new char[32];
        i2c[m2i(OWN)] = C_ADMIN;
        i2c[m2i(USE)] = C_USER;
        i2c[m2i(READ)] = C_READ;
        i2c[m2i(WRITE)] = C_WRITE;
        i2c[m2i(EXECUTE)] = C_EXECUTE;
        i2c[m2i(CREATE)] = C_CREATE;
        i2c[m2i(DELETE)] = C_DELETE;

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
            sb.append(modeSeparator);
            formatBits(sb, denyBits);
        }

        return sb.toString();
    }

    public String getReadableString() {
        return getReadableString(true);
    }

    public String getReadableString(boolean allowOrDeny) {
        int bits = allowOrDeny ? getAllowBits() : getDenyBits();
        List<String> words = new ArrayList<String>();
        if ((bits & OWN) != 0)
            words.add("管理");
        if ((bits & USE) != 0)
            words.add("使用");
        if ((bits & CREATE) != 0)
            words.add("创建");
        if ((bits & READ) != 0)
            words.add("读取");
        if ((bits & WRITE) != 0)
            words.add("写入");
        if ((bits & DELETE) != 0)
            words.add("删除");
        if ((bits & EXECUTE) != 0)
            words.add("执行");

        StringBuilder sb = null;
        for (String word : words) {
            if (sb == null)
                sb = new StringBuilder();
            else
                sb.append(", ");
            sb.append(word);
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
