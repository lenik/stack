package com.bee32.sem.misc;

import javax.free.IllegalUsageException;

import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.orm.util.EntityViewBean;

public abstract class AbstractSimpleEntityView
        extends EntityViewBean
        implements ISimpleEntityView {

    private static final long serialVersionUID = 1L;

    /** Delete entities with user lock */
    protected static final int DELETE_FORCE = 1;
    /** Detach the entity before deletion */
    protected static final int DELETE_DETACH = 2;
    /** Don't refresh row count */
    protected static final int DELETE_NO_REFRESH = 128;

    /** Force to update locked entities */
    protected static final int SAVE_FORCE = 1;
    /** Save entities with user lock on */
    protected static final int SAVE_LOCKED = 2;
    /** Save entities with user lock off. */
    protected static final int SAVE_UNLOCKED = 4;
    /** Save entities with user lock off, unlock at first if necessary. */
    protected static final int SAVE_FORCE_UNLOCKED = SAVE_FORCE | SAVE_UNLOCKED;
    /** Change the entities' owner to current user before save */
    protected static final int SAVE_CHOWN = 8;
    /** Only create, never update */
    protected static final int SAVE_NOEXIST = 16;
    /** Only update, never create */
    protected static final int SAVE_MUSTEXIST = 32;
    /** Don't refresh row count */
    protected static final int SAVE_NO_REFRESH = 128;
    /** Used by saveDup */
    protected static final int SAVE_CONT = 256;
    /** Used by saveDup */
    protected static final int SAVE_DUP = 512;

    protected int saveFlags = 0;
    protected int deleteFlags = 0;

    boolean checkDuplicatesBeforeCreate = true;
    boolean checkDuplicatedLabel = true;

    @Override
    public boolean isCheckDuplicatesBeforeCreate() {
        return checkDuplicatesBeforeCreate;
    }

    @Override
    public void setCheckDuplicatesBeforeCreate(boolean checkDuplicatesBeforeCreate) {
        if (this.checkDuplicatedLabel != checkDuplicatesBeforeCreate) {
            if (checkDuplicatesBeforeCreate)
                uiLogger.info("检查关键字重复功能开启。");
            else
                uiLogger.info("检查关键字重复功能关闭。");
            this.checkDuplicatesBeforeCreate = checkDuplicatesBeforeCreate;
        }
    }

    public String getLoggedInUserName() {
        SessionUser sessionUser = SessionUser.getInstance();
        String myName = sessionUser.getUser().getName();
        return myName;
    }

    public String getLoggedInUserDisplayName() {
        SessionUser sessionUser = SessionUser.getInstance();
        String myName = sessionUser.getUser().getDisplayName();
        return myName;
    }

    static void checkSaveFlags(int saveFlags) {
        int forceLockedMask = SAVE_FORCE | SAVE_LOCKED;
        if ((saveFlags & forceLockedMask) == forceLockedMask)
            throw new IllegalUsageException("SAVE_FORCE and SAVE_LOCKED are exclusive.");
    }

    static void checkDeleteFlags(int deleteFlags) {
    }

    @Operation
    public final boolean save() {
        return save(saveFlags, null);
    }

    protected final boolean save(boolean createOrUpdate) {
        return save(createOrUpdate ? SAVE_NOEXIST : SAVE_MUSTEXIST, null);
    }

    protected boolean save(int saveFlags, String hint) {
        checkSaveFlags(saveFlags);
        boolean creating = isCreating() || (saveFlags & SAVE_DUP) != 0;

        if (hint == null)
            // if ((saveFlags & SAVE_MUSTEXIST) != 0)
            hint = creating ? "保存" : "更新";

        if (getOpenedObjects().isEmpty()) {
            uiLogger.error("没有需要" + hint + "的对象!");
            return false;
        }

        if (!saveImpl(saveFlags, hint, creating))
            return false;

        if ((saveFlags & SAVE_CONT) == 0)
            showIndex(creating ? 1 : null);
        return true;
    }

    protected abstract boolean saveImpl(int saveFlags, String hint, boolean creating);

}
