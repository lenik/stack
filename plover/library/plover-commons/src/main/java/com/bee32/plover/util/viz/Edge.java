package com.bee32.plover.util.viz;

public class Edge
        extends GraphObject {

    Node from;
    Node to;
    EdgeDirection direction;

    public Edge(Node from, Node to, EdgeDirection direction) {
        if (from == null)
            throw new NullPointerException("from");
        if (to == null)
            throw new NullPointerException("to");
        if (direction == null)
            throw new NullPointerException("direction");
        this.from = from;
        this.to = to;
        this.direction = direction;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public EdgeDirection getDirection() {
        return direction;
    }

    public void setDirection(EdgeDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(from.toString());
        sb.append(' ');
        sb.append(direction.getSymbol());
        sb.append(' ');
        sb.append(to.toString());
        return sb.toString();
    }

}
