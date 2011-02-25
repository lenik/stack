package com.bee32.plover.model.qualifier;

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
    public boolean equalsSpecific(Position o) {
        if (!position.equals(o.position))
            return false;
        return true;
    }

    @Override
    public int compareSpecific(Position o) {
        return position.compareTo(o.position);
    }

}
