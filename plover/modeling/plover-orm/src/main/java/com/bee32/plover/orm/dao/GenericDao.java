package com.bee32.plover.orm.dao;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;

@ComponentTemplate
@Lazy
public abstract class GenericDao
        extends HibernateDaoSupport {

}
