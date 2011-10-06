package com.bee32.plover.util.viz;

import static org.kohsuke.graphviz.Attribute.COLOR;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.graphviz.Attribute;
import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Style;

public class SimpleTest
        extends Assert {

    @Test
    public void test1()
            throws IOException, InterruptedException {
        Graph graph = new Graph();
        Style s = new Style();
        s.attr(COLOR, Color.RED);
        graph.nodeWith(s).node("a").to().node("b").generateTo(Arrays.asList("dot", "-Tgif"), new File("test1.gif"));
    }

    @Test
    public void test2()
            throws IOException, InterruptedException {
        Graph graph = new Graph();
        Style s = new Style();
        s.attr("html",
                "<TABLE BORDER=\"0\"><TR><TD><IMG SRC=\"package.png\" /></TD></TR><TR><TD>caption</TD></TR></TABLE>");
        s.attr(Attribute.FONTNAME, "sans serif");
        s.attr(Attribute.FONTSIZE, 10f);
        s.attr(Attribute.SHAPE, org.kohsuke.graphviz.Shape.NONE);
        s.attr(Attribute.URL, "http://www.sun.com/");
        graph.nodeWith(s);
        graph.node("a").to().node("b").to().node("c");
        graph.generateTo(Arrays.asList("dot", "-Tcmapx", "-ox.map", "-Tgif"), new File("test2.gif"));
    }

}