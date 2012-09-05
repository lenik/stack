package com.bee32.sem.salary.util;

import java.util.Arrays;
import java.util.List;

import com.bee32.sem.salary.entity.SalaryElementDef;

public class TreeTest {

    public static void main(String[] args) {
        SalaryElementDef def1 = new SalaryElementDef();
        def1.setCategory("A/B/C");
        def1.setOrder(1);

        SalaryElementDef def2 = new SalaryElementDef();
        def2.setCategory("B/C/E");
        def2.setOrder(2);

        SalaryTreeNode root = new SalaryTreeNode();
        root.setLabel("root");

        for (SalaryElementDef def : Arrays.asList(def1, def2)) {
            SalaryTreeNode node = root.resolve(def.getCategory());
            node.setOrder(def.getOrder());
        }

        levelTest(root);
    }

    static void levelTest(SalaryTreeNode root) {

        List<SalaryTreeNode> children = root.getChildren();
        StringBuilder sb = new StringBuilder();
        for (SalaryTreeNode node : children) {
            sb.append(node.getLabel());
            sb.append(",");
        }
        System.out.println(sb.toString());
        for (SalaryTreeNode node : children)
            levelTest(node);

    }
}
