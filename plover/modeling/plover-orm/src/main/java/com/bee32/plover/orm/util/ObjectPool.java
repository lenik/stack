package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class ObjectPool {

    int nextId;
    Map<Object, Integer> alloc = new IdentityHashMap<Object, Integer>();

    public int getId(Object obj) {
        Integer id = alloc.get(obj);
        if (id == null) {
            id = ++nextId;
            alloc.put(obj, id);
        }
        return id;
    }

    static Map<Class<?>, ObjectPool> pools = new HashMap<Class<?>, ObjectPool>();

    public static synchronized ObjectPool forClass(Class<?> clazz) {
        ObjectPool pool = pools.get(clazz);
        if (pool == null) {
            pool = new ObjectPool();
            pools.put(clazz, pool);
        }
        return pool;
    }

    public static int id(Object obj) {
        if (obj == null)
            return 0;
        ObjectPool pool = forClass(obj.getClass());
        return pool.getId(obj);
    }

    public static void main(String[] args) {
        String a = "hay";
        String b = "hay";
        String c = "world";
        System.out.println(ObjectPool.id(a));
        System.out.println(ObjectPool.id(b));
        System.out.println(ObjectPool.id(c));
    }

}
