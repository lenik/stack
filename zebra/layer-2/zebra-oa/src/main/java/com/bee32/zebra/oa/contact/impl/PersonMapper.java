package com.bee32.zebra.oa.contact.impl;

import java.util.List;

import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.util.F_WordCount;

public interface PersonMapper
        extends IMapperTemplate<Person, PersonCriteria> {

    List<F_WordCount> histoBySurname();

}
