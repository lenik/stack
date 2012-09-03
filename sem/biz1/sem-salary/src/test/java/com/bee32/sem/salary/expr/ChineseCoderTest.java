package com.bee32.sem.salary.expr;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.salary.util.ChineseCodec;

public class ChineseCoderTest
        extends Assert {

    @Test
    public void testEncodeEnglish() {
        String encoded = ChineseCodec.encode("hello");
        assertEquals("hello", encoded);
    }

    @Test
    public void testDecodeEnglish() {
        String decoded = ChineseCodec.decode("hello");
        assertEquals("hello", decoded);
    }

    @Test
    public void testEncodeZh1() {
        String encoded = ChineseCodec.encode("你");
        assertEquals("_u4f60_", encoded);
    }

    @Test
    public void testEncodeZh2() {
        String encoded = ChineseCodec.encode("你好");
        assertEquals("_u4f60__u597d_", encoded);
    }

    @Test
    public void testEncodeMixed() {
        String encoded = ChineseCodec.encode("a你b好");
        assertEquals("a_u4f60_b_u597d_", encoded);
    }

    @Test
    public void testEncodeAmbiguous() {
        String decoded = ChineseCodec.decode("_u123_");
        assertEquals("_u123_", decoded);
    }

    @Test
    public void testDecodeZh1() {
        String decoded = ChineseCodec.decode("_u4f60_");
        assertEquals("你", decoded);
    }

    @Test
    public void testDecodeZh2() {
        String decoded = ChineseCodec.decode("_u4f60__u597d_");
        assertEquals("你好", decoded);
    }

    @Test
    public void testDecodeMixed() {
        String decoded = ChineseCodec.decode("a_u4f60_b_u597d_");
        assertEquals("a你b好", decoded);
    }

    @Test
    public void testDecodeAmbiguous() {
        String decoded = ChineseCodec.decode("_u123_");
        assertEquals("_u123_", decoded);
    }

}
