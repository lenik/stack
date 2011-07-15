package com.bee32.sem.inventory;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.free.IIndentedOut;
import javax.free.IndentedOutImpl;
import javax.free.PrintStreamPrintOut;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.SamplePackage;

public class SampleDumper {

    public static void dump(SamplePackage node) {
        PrintStreamPrintOut out = new PrintStreamPrintOut(System.out);
        dump(node, new IndentedOutImpl(out));
    }

    static void dump(SamplePackage node, IIndentedOut out) {
        if (node == null)
            throw new NullPointerException("node");

        out.println("+ " + node.getName());
        out.enter();

        for (Entity<?> entity : node.getInstances()) {
            out.println(entity.getClass().getSimpleName() + " : " + entity.getId());
        }

        for (SamplePackage dep : node.getDependencies())
            dump(dep, out);

        out.leave();
    }

    public static void checkUnique(SamplePackage start) {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
        checkUnique(start, map);
    }

    static void checkUnique(SamplePackage node, Map<String, Object> map) {
        String clazz = node.getName();
        Object existing = map.get(clazz);
        if (existing != null) {
            if (existing != node)
                throw new IllegalStateException("Node " + node + " has different instances.");
        }

        map.put(clazz, node);

        for (SamplePackage dep : node.getDependencies())
            checkUnique(dep, map);
    }

}
