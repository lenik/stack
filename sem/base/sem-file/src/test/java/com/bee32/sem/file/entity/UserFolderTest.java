package com.bee32.sem.file.entity;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.file.dto.UserFolderDto;

public class UserFolderTest
        extends Assert {

    @Test
    public void testGetPath() {
        UserFolder a = new UserFolder(null, "a");
        UserFolderDto aDto = DTOs.marshal(UserFolderDto.class, a);
        assertEquals(aDto.getPath(), "a");

        UserFolder b = new UserFolder(a, "book");
        UserFolderDto bDto = DTOs.marshal(UserFolderDto.class, b);
        assertEquals(bDto.getPath(), "a/book");

        UserFolder c = new UserFolder(b, "cat");
        UserFolderDto cDto = DTOs.marshal(UserFolderDto.class, c);
        assertEquals(cDto.getPath(), "a/book/cat");
    }

}
