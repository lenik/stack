package com.bee32.sem.people.web;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class PermissionAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {

    }

}
