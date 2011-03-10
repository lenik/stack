package com.bee32.icsf.principal;

import java.util.Map;

import com.bee32.plover.arch.Component;

public class SampleStore {

    public GroupBean sunCorp;
    public GroupBean solaGroup;

    public RoleBean adminRole;
    public RoleBean registeredRole;

    public UserBean eva;
    public UserBean wallE;
    public UserBean alice;
    public UserBean tom;
    public UserBean kate;

    {
        sunCorp = new Group
        tom = new UserBean("Tom", sunCorp, adminRole);

    }

    /**
     * Who git it to me?
     */
    static protected Map<String, Component> type;

    static final SampleStore instance = new SampleStore();

    public static SampleStore getInstance() {
        return instance;
    }

}
