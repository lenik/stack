package com.bee32.zebra.oa.contact.impl;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import net.bodz.bas.t.map.MapDumper;

import com.bee32.zebra.tk.sql.TestEnvironment;
import com.bee32.zebra.tk.sql.VhostDataService;

public class PersonMapperTest {

    PersonMapper mapper;

    @Before
    public void setUp() {
        TestEnvironment.setUpVhosts();
        mapper = VhostDataService.forCurrentRequest().query(PersonMapper.class);
    }

    @Test
    public void testCountMask() {
        PersonMask mask = new PersonMask();
        mask.type = PartyType.SUPPLIER;
        Map<String, Number> map = mapper.count(null);
        new MapDumper().dump(map);
    }

}
