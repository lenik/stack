package com.bee32.sem.chance.web;

import com.bee32.sem.sandbox.MultiTabEntityVdx;

public abstract class ChanceBeanVdx
        extends MultiTabEntityVdx {

    private static final long serialVersionUID = 1L;

    public static final int TAB_INDEX = 0;
    public static final int TAB_FORM = 1;

    static enum State {
        INDEX, //
        EDIT, //
        VIEW, //
        CREATE, // XXX

        CHOOSE_ACTION, //
        VIEW_ACTION, //

        CREATE_Q, //
        EDIT_Q, //
        VIEW_Q, //
    }

    State state = State.INDEX;
    boolean roleRendered;

    boolean isSearching;

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isRoleRendered() {
        return roleRendered;
    }

    public void setRoleRendered(boolean roleRendered) {
        this.roleRendered = roleRendered;
    }

}
