package com.bee32.plover.arch.util;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.free.Person;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.bean.BeanOfCollection;

public class BeanOfCollectionTest
        extends Assert {

    @Test
    public void testTransformMap()
            throws IntrospectionException {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("Lenik", 18, false));
        persons.add(new Person("Lily", 17, true));
        Map<String, ? extends Collection<?>> personTr = BeanOfCollection.transformMap(Person.class, persons);

        // System.out.println("names: " + personTr.get("names"));
        // System.out.println("ages: " + personTr.get("ages"));
        assertEquals(Arrays.asList("Lenik", "Lily"), personTr.get("names"));
        assertEquals(Arrays.asList(18, 17), personTr.get("ages"));
    }

}
