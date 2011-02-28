package com.bee32.plover.arch.ui.res;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.arch.ui.IRefdocEntry;
import com.bee32.plover.arch.ui.RefdocsSample;
import com.bee32.plover.arch.ui.res.InjectedRefdocs;
import com.bee32.plover.arch.util.res.LoopbackDispatcher;

public class InjectedRefdocsTest {

    InjectedRefdocs sink;

    public InjectedRefdocsTest() {
        sink = new InjectedRefdocs(RefdocsSample.class);
        LoopbackDispatcher dispatcher = new LoopbackDispatcher(sink);
        dispatcher.dispatchClassResource(RefdocsSample.class, Locale.ENGLISH);
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
