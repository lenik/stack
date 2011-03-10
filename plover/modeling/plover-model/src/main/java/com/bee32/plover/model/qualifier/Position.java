package com.bee32.plover.model.qualifier;

import com.bee32.plover.arch.Component;

public class Position
        extends Qualifier<Position> {

    private static final long serialVersionUID = 1L;

    private String position;

    public Position(String position) {
        super(Position.class);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public int hashCodeSpecific() {
        int hash = position.hashCode();
        return hash;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        Position o = (Position) obj;

        if (!position.equals(o.position))
            return false;

        return true;
    }

    @Override
    public int compareSpecificTo(Position o) {
        return position.compareTo(o.position);
    }

}
