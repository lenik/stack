package com.bee32.sem.people.util;

import java.util.List;

import com.bee32.sem.people.dto.ContactDto;

public class PeopleUtil {
    public static String getCombinedContacts(List<ContactDto> contacts) {
        if (contacts == null)
            return "";
        if (contacts.size() <= 0)
            return "";

        StringBuilder builder = new StringBuilder();
        for (ContactDto contact : contacts) {
            if (builder.length() > 0
                    && (contact.getTel() != null && contact.getTel().length() > 0))
                builder.append(",");
            builder.append(contact.getTel());

            if (builder.length() > 0
                    && (contact.getMobile() != null && contact.getMobile().length() > 0))
                builder.append(",");
            builder.append(contact.getMobile());


            if (builder.length() > 0
                    && (contact.getFax() != null && contact.getFax().length() > 0))
                builder.append(",");
            builder.append(contact.getFax());
        }
        return builder.toString();
    }
}
