package com.bee32.plover.restful;

public interface CommonVerbs {

    Verb GET = new Verb("get");

    Verb CREATE = new Verb("create", true); // PUT

    Verb UPDATE = new Verb("update"); // POST

    Verb DELETE = new Verb("delete", true);

    Verb HEAD = new Verb("head");

}
