package com.bee32.plover.restful;

public class Verbs {

    public static final Verb GET = new Verb("get");

    public static final Verb CREATE = new Verb("create", true); // PUT

    public static final Verb UPDATE = new Verb("update"); // POST

    public static final Verb DELETE = new Verb("delete", true);

    public static final Verb ESTATE = new Verb("head");

    public static final Verb NEW = new Verb("new");

}
