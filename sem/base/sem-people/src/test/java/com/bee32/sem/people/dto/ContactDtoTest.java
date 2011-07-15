package com.bee32.sem.people.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

public class ContactDtoTest
        extends Assert {

    @Test
    public void testSerialization()
            throws IOException, ClassNotFoundException {
        ContactDto c = new ContactDto().create();
        c.setEmail("a@b.com");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(c);
        objOut.close();

        byte[] data = out.toByteArray();

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream objIn = new ObjectInputStream(in);
        Object obj = objIn.readObject();
        ContactDto c2 = (ContactDto) obj;
        assertEquals(c.getEmail(), c2.getEmail());
    }

}
