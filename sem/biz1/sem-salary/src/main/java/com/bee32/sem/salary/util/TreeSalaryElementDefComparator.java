package com.bee32.sem.salary.util;

import java.util.Comparator;

import javax.free.Nullables;

import com.bee32.sem.salary.entity.SalaryElementDef;

public class TreeSalaryElementDefComparator
        implements Comparator<SalaryElementDef> {

    SalaryTreeNode root;

    public TreeSalaryElementDefComparator(SalaryTreeNode root) {
        this.root = root;
    }

    @Override
    public int compare(SalaryElementDef o1, SalaryElementDef o2) {
        String path1 = o1.getPath();
        String path2 = o2.getPath();
        SalaryTreeNode node1 = root.resolve(path1);
        SalaryTreeNode node2 = root.resolve(path2);
        int[] array1 = node1.getOrderArray();
        int[] array2 = node2.getOrderArray();
        String[] array3 = node1.getLabelArray();
        String[] array4 = node2.getLabelArray();
        int length = Math.max(array1.length, array2.length);
        for (int i = 0; i < length; i++) {
            int order1 = i < array1.length ? array1[i] : 0;
            int order2 = i < array2.length ? array2[i] : 0;
            if (order1 != order2)
                return order1 - order2;
            String label1 = i < array3.length ? array3[i] : null;
            String label2 = i < array4.length ? array4[i] : null;
            if (!Nullables.equals(label1, label2))
                return label1.compareTo(label2);
        }

        return 0;
    }

}