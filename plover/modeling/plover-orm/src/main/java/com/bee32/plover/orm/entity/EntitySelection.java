package com.bee32.plover.orm.entity;

import java.util.Arrays;
import java.util.Collection;

import com.bee32.plover.arch.util.Flags32;

public final class EntitySelection {

    final Collection<Entity<?>> selection;

    public EntitySelection(Entity<?>... selection) {
        if (selection == null)
            throw new NullPointerException("selection");
        this.selection = Arrays.asList(selection);
    }

    public EntitySelection(Collection<Entity<?>> selection) {
        if (selection == null)
            throw new NullPointerException("selection");
        this.selection = selection;
    }

    public final void init(Flags32 flags32) {
        for (Entity<?> e : selection)
            e.getEntityFlags().init(flags32);
    }

    public final void init(int bits) {
        for (Entity<?> e : selection)
            e.getEntityFlags().init(bits);
    }

    public final void set(int mask, boolean on) {
        for (Entity<?> e : selection)
            e.getEntityFlags().set(mask, on);
    }

    public final void set(int mask) {
        for (Entity<?> e : selection)
            e.getEntityFlags().set(mask);
    }

    public final void clear(int mask) {
        for (Entity<?> e : selection)
            e.getEntityFlags().clear(mask);
    }

    public final void toggle(int mask) {
        for (Entity<?> e : selection)
            e.getEntityFlags().toggle(mask);
    }

    public void setMarked(boolean marked) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setMarked(marked);
    }

    public void setDeleted(boolean deleted) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setDeleted(deleted);
    }

    public void setLocked(boolean locked) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setLocked(locked);
    }

    public void setUserLock1(boolean userLock1) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserLock1(userLock1);
    }

    public void setUserLock2(boolean userLock2) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserLock2(userLock2);
    }

    public void setUserLock3(boolean userLock3) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserLock3(userLock3);
    }

    public void setExtra(boolean extra) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setExtra(extra);
    }

    public void setExtra2(boolean extra2) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setExtra2(extra2);
    }

    public void setUserSel1(boolean userSel1) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserSel1(userSel1);
    }

    public void setUserSel2(boolean userSel2) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserSel2(userSel2);
    }

    public void setUserSel3(boolean userSel3) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUserSel3(userSel3);
    }

    public void setWarn(boolean warn) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setWarn(warn);
    }

    public void setError(boolean error) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setError(error);
    }

    public void setBold(boolean bold) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setBold(bold);
    }

    public void setItalic(boolean italic) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setItalic(italic);
    }

    public void setUnderline(boolean underline) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setUnderline(underline);
    }

    public void setStrikeline(boolean strikeline) {
        for (Entity<?> e : selection)
            e.getEntityFlags().setStrikeline(strikeline);
    }

}
