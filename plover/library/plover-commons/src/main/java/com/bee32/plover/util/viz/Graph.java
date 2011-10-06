package com.bee32.plover.util.viz;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Graph
        extends GraphObject {

    String label;
    String labeljust = "l";
    String labelloc = "t";

    int ranksep = 1;
    String rankdir = "TB";

    float nodesep = .05f;

    Set<Node> nodes = new HashSet<Node>();
    Set<Edge> edges = new HashSet<Edge>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void clear() {
        nodes.clear();
        edges.clear();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public boolean addNode(Node node) {
        return nodes.add(node);
    }

    public boolean removeNode(Node node) {
        return nodes.remove(node);
    }

    public boolean addEdge(Edge edge) {
        addNode(edge.getFrom());
        addNode(edge.getTo());
        return edges.add(edge);
    }

    public boolean removeEdge(Edge edge) {
        return edges.remove(edge);
    }

    public void addEdge(Node src, Node dst) {
        Edge edge = new Edge(src, dst, EdgeDirection.NONE);
        addEdge(edge);
    }

    public void addLink(Node src, Node dst) {
        Edge edge = new Edge(src, dst, EdgeDirection.UNI);
        addEdge(edge);
    }

    public void removeDanglingNodes() {
        Set<Node> linked = new HashSet<Node>(nodes.size());
        for (Edge edge : edges) {
            linked.add(edge.getFrom());
            linked.add(edge.getTo());
        }
        nodes.retainAll(linked);
    }

    public void removeDanglingEdges() {
        Iterator<Edge> it = edges.iterator();
        while (it.hasNext()) {
            Edge edge = it.next();
            boolean badFrom = !nodes.contains(edge.getFrom());
            boolean badTo = !nodes.contains(edge.getTo());
            if (badFrom || badTo)
                it.remove();
        }
    }

}
