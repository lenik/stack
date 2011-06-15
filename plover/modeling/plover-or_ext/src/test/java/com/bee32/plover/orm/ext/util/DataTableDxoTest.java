package com.bee32.plover.orm.ext.util;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.orm.web.util.DataTableDxo;

/**
 * Gson won't encode array[ array[] ]. Why? ...
 */
public class DataTableDxoTest
        extends Assert {

    @Test
    public void testToJson() {
        DataTableDxo tab = new DataTableDxo();
        tab.columnCount = 3;

        tab.push("magic");
        tab.push(Arrays.asList("stuff"));
        tab.next();

        Map<String, Object> map = tab.exportMap();
        String json = JsonUtil.dump(map);
        // System.out.println(json);

        assertTrue(json.contains("magic"));
        assertTrue(json.contains("stuff"));
    }

}
