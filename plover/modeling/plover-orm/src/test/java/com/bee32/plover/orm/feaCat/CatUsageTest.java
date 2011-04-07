package com.bee32.plover.orm.feaCat;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.BuildException;
import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CatDao;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Import(FeatureConfig.class)
@Using(Animals.class)
public class CatUsageTest
        extends WiredDaoTestCase {

    static Logger logger = LoggerFactory.getLogger(CatUsageTest.class);

    @Inject
    FeaturePlayer catService;

    @Test
    public void testPopulateStruct()
            throws BuildException {
        IStruct struct = new MapStruct();
        struct.put("name", "kitty");
        struct.put("color", "pink");

        Cat expected = new Cat("kitty", "pink");

        Cat actual = new CatDao().populate(struct);

        assertEquals(expected, actual);
    }

    @Test
    public void testCascade() {
        logger.warn("Do Prepare");
        catService.tcPrepare();

        logger.warn("Do List");
        catService.tcList();
// catService.tcList();
    }

}
