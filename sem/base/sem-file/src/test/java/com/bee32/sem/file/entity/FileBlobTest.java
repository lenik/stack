package com.bee32.sem.file.entity;

import java.io.File;
import java.io.IOException;

import javax.free.JavaioFile;
import javax.free.TempFile;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.entity.EntityAccessor;

public class FileBlobTest
        extends Assert {

    @Test
    public void testCommitEmptyFile()
            throws IOException {
        File file1 = TempFile.createTempFile();
        new JavaioFile(file1).forWrite().write("");
        FileBlob blob = FileBlob.commit(file1, true);
        String digest = blob.getDigest();
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", digest);

        FileBlob q = new FileBlob();
        EntityAccessor.setId(q, digest);
        String contents = q.resolve().forRead().readTextContents();
        assertEquals("", contents);
    }

    @Test
    public void testCommitSimpleFile()
            throws IOException {
        File file1 = TempFile.createTempFile();
        new JavaioFile(file1).forWrite().write("Hello, world!");
        FileBlob blob = FileBlob.commit(file1, true);
        String digest = blob.getDigest();
        assertEquals("6cd3556deb0da54bca060b4c39479839", digest);

        FileBlob q = new FileBlob();
        EntityAccessor.setId(q, digest);
        String contents = q.resolve().forRead().readTextContents();
        assertEquals("Hello, world!", contents);
    }

}
