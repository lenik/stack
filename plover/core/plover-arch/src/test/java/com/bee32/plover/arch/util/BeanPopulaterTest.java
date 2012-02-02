package com.bee32.plover.arch.util;

import java.util.HashMap;
import java.util.Map;

import javax.free.Person;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.bean.BeanPopulater;

public class BeanPopulaterTest
        extends Assert {

    void testPerson(PersonBean person)
            throws BuildException {
        Map<String, String> struct = new HashMap<String, String>();

        struct.put("name", "" + person.getName());
        struct.put("age", "" + person.getAge());
        struct.put("gender", "" + person.getGender());

        Person o = new Person();
        int changes = BeanPopulater.populate(o, struct);
        assertEquals(0, changes);

        assertEquals(person, o);
    }

    @Test
    /* Traits package, needs update. */@Ignore
    public void testPersonSamples()
            throws BuildException {
        testPerson(PersonBean.Mike);
        testPerson(PersonBean.Milk);
    }

}
