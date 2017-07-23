package com.bee32.zebra.oa.contact.impl;

import java.util.List;

import net.bodz.lily.model.util.F_WordCount;

import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.sql.FooMapper;

public interface PersonMapper
        extends FooMapper<Person, PersonMask> {

    List<F_WordCount> histoBySurname();

}
