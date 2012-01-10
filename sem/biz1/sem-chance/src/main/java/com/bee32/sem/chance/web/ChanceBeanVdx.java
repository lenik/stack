package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.sandbox.MultiTabEntityVdx;

public abstract class ChanceBeanVdx
        extends MultiTabEntityVdx {

    private static final long serialVersionUID = 1L;

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

    public ChanceBeanVdx() {
        super(Chance.class, ChanceDto.class, 0);
    }

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
