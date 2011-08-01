package com.bee32.plover.orm.entity;

import com.bee32.plover.arch.util.Flags32;

public class EntityFlags
        extends Flags32 {

    private static final long serialVersionUID = 1L;

    public static final int MARKED = 1 << 0;
    public static final int DELETED = 1 << 1;
    public static final int LOCKED = 1 << 2;

    public static final int USER_LOCK1 = 1 << 3;
    public static final int USER_LOCK2 = 1 << 4;
    public static final int USER_LOCK3 = 1 << 5;

    public static final int EXTRA = 1 << 6;
    public static final int EXTRA2 = 1 << 7;

    public static final int USER_SEL1 = 1 << 8;
    public static final int USER_SEL2 = 1 << 9;
    public static final int USER_SEL3 = 1 << 10;

    public static final int WARN = 1 << 11;
    public static final int ERROR = 1 << 12;

    public static final int BOLD = 1 << 16;
    public static final int ITALIC = 1 << 17;
    public static final int UNDERLINE = 1 << 18;
    public static final int STRIKELINE = 1 << 19;

    public EntityFlags() {
        super();
    }

    public EntityFlags(int flags) {
        super(flags);
    }

    public boolean isMarked() {
        return test(MARKED);
    }

    public void setMarked(boolean marked) {
        set(MARKED, marked);
    }

    public boolean isDeleted() {
        return test(DELETED);
    }

    public void setDeleted(boolean deleted) {
        set(DELETED, deleted);
    }

    public boolean isLocked() {
        return test(LOCKED);
    }

    public void setLocked(boolean locked) {
        set(LOCKED, locked);
    }

    public boolean isUserLock1() {
        return test(USER_LOCK1);
    }

    public void setUserLock1(boolean userLock1) {
        set(USER_LOCK1, userLock1);
    }

    public boolean isUserLock2() {
        return test(USER_LOCK2);
    }

    public void setUserLock2(boolean userLock2) {
        set(USER_LOCK2, userLock2);
    }

    public boolean isUserLock3() {
        return test(USER_LOCK3);
    }

    public void setUserLock3(boolean userLock3) {
        set(USER_LOCK3, userLock3);
    }

    public boolean isExtra() {
        return test(EXTRA);
    }

    public void setExtra(boolean extra) {
        set(EXTRA, extra);
    }

    public boolean isExtra2() {
        return test(EXTRA2);
    }

    public void setExtra2(boolean extra2) {
        set(EXTRA2, extra2);
    }

    public boolean isUserSel1() {
        return test(USER_SEL1);
    }

    public void setUserSel1(boolean userSel1) {
        set(USER_SEL1, userSel1);
    }

    public boolean isUserSel2() {
        return test(USER_SEL2);
    }

    public void setUserSel2(boolean userSel2) {
        set(USER_SEL2, userSel2);
    }

    public boolean isUserSel3() {
        return test(USER_SEL3);
    }

    public void setUserSel3(boolean userSel3) {
        set(USER_SEL3, userSel3);
    }

    public boolean isWarn() {
        return test(WARN);
    }

    public void setWarn(boolean warn) {
        set(WARN, warn);
    }

    public boolean isError() {
        return test(ERROR);
    }

    public void setError(boolean error) {
        set(ERROR, error);
    }

    public boolean isBold() {
        return test(BOLD);
    }

    public void setBold(boolean bold) {
        set(BOLD, bold);
    }

    public boolean isItalic() {
        return test(ITALIC);
    }

    public void setItalic(boolean italic) {
        set(ITALIC, italic);
    }

    public boolean isUnderline() {
        return test(UNDERLINE);
    }

    public void setUnderline(boolean underline) {
        set(UNDERLINE, underline);
    }

    public boolean isStrikeline() {
        return test(STRIKELINE);
    }

    public void setStrikeline(boolean strikeline) {
        set(STRIKELINE, strikeline);
    }

}
