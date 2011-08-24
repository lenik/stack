package com.bee32.sem.mail;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.arch.util.EnumAltComparator;

public class MailTypeTest {

    static {
        Locale.setDefault(Locale.ROOT);
    }

    @Test
    public void testNls() {
        String bcastName = MailType.BCAST.getDisplayName();
        assertEquals("Broadcast Message", bcastName);
    }

    public static void main(String[] args) {
        EnumAltComparator<MailType> cmp = new EnumAltComparator<MailType>();
        ArrayList<MailType> list = new ArrayList<MailType>(MailType.values());
        Collections.sort(list, cmp);
        for (MailType mt : list) {
            System.out.println(mt);
        }
    }

}
