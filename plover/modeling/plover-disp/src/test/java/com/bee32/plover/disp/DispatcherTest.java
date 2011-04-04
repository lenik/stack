package com.bee32.plover.disp;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.coll.ListDispatcher;
import com.bee32.plover.disp.coll.MapDispatcher;
import com.bee32.plover.disp.dyna.DynamicDispatcher;
import com.bee32.plover.disp.tree.NamedNodeDispatcher;
import com.bee32.plover.disp.type.FieldDispatcher;
import com.bee32.plover.disp.type.MethodDispatcher;
import com.bee32.plover.disp.type.PropertyDispatcher;
import com.bee32.plover.disp.util.DispatchUtil;

public class DispatcherTest
        extends Assert {

    @Test
    public void testLoadProviders() {
        Dispatcher d = new Dispatcher();
        Set<Class<?>> cs = new HashSet<Class<?>>();
        for (IDispatcher id : d.dispatchers) {
            System.out.println(id);
            cs.add(id.getClass());
        }

        Class<?>[] classes = {
                //

                ListDispatcher.class, //
                MapDispatcher.class, //
                NamedNodeDispatcher.class, //
                FieldDispatcher.class, //
                PropertyDispatcher.class, //
                MethodDispatcher.class, //
                DynamicDispatcher.class //
        };
        for (Class<?> c : classes) {
            assertTrue(c.getName(), cs.contains(c));
        }
    }

    public class Home {
        public String address = "coridor";

        public String getCity() {
            return "Punjab";
        }

        public String road() {
            return "very long";
        }
    }

    @Test
    public void testMixed()
            throws DispatchException {
        Home home = new Home();
        Dispatcher d = new Dispatcher();
        assertEquals("coridor", DispatchUtil.dispatch(d, home, "address"));
        assertEquals("Punjab", DispatchUtil.dispatch(d, home, "city"));
        assertEquals("very long", DispatchUtil.dispatch(d, home, "road"));
    }

}
