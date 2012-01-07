package com.bee32.plover.restful.resource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.PloverServletModule;

public class ModuleObjectPageDirectoryTest
        extends Assert {

    @Test
    public void testLocalView() {
        PloverServletModule module = new PloverServletModule();
        ModuleObjectPageDirectory pageDir = new ModuleObjectPageDirectory(module);
        pageDir.addLocalPageForView("greeting", "hey/abc");
        Location loc = pageDir.getPagesForView("greeting").get(0);
        // System.out.println(loc);
        assertEquals("/3/12/2/10/hey/abc", loc.getBase());
    }

    @Test
    public void testFormatHref() {
        PloverServletModule module = new PloverServletModule();
        ModuleObjectPageDirectory pageDir = new ModuleObjectPageDirectory(module);
        pageDir.addLocalPageForView("greeting", "hey/abc");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", 123);
        params.put("name", "彩蝶");
        Location loc = pageDir.getPagesForView("greeting", params).get(0);
        // System.out.println(loc);
        assertEquals("/3/12/2/10/hey/abc?id=123&name=%E5%BD%A9%E8%9D%B6", loc.getBase());
    }

}
