package com.bee32.zebra.oa.contact.impl;

import java.util.List;

import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_WordCount;

public interface PersonMapper
        extends FooMapper<Person, PersonCriteria> {

    List<F_WordCount> histoBySurname();

}
