package com.bee32.plover.arch.ui.res;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.bee32.plover.arch.ui.IRefdocEntry;
import com.bee32.plover.arch.ui.RefdocsSample;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.PropertyDispatcher;

public class InjectedRefdocsTest {

    InjectedRefdocs sink;

    public InjectedRefdocsTest() {
        Class<?> baseClass = RefdocsSample.class;

        sink = new InjectedRefdocs(ClassUtil.getContextURL(baseClass));

        new PropertyDispatcher(baseClass).setRootAcceptor(sink).pull();
    }

    @Test
    public void test1()
            throws Exception {
        String[] tags = sink.getTags().toArray(new String[0]);
        Arrays.sort(tags);

        // TreeMap required.
        assertArrayEquals(new String[] { "2009", "refspec", "simple", "tutorial", },//
                tags);

        IRefdocEntry tutorial = sink.getDefaultEntry("tutorial");
        assertEquals("Simple Tutorial", tutorial.getTitle());

        IRefdocEntry refspec = sink.getDefaultEntry("refspec");
        assertEquals("Reference Specification 2009", refspec.getTitle());

        InputStream in = refspec.getURL().openStream();
        byte[] block = new byte[1024];
        int cb = in.read(block);
        in.close();
        String content = new String(block, 0, cb, "utf-8");
        assertEquals("A test resource page. ", content);
    }

}
