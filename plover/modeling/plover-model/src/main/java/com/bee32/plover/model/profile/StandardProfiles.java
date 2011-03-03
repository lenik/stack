package com.bee32.plover.model.profile;

public interface StandardProfiles {

    Profile CONTENT = new Profile("content");
    Profile BRIEF = new Profile("brief", CONTENT);
    Profile LIST_ENTRY = new Profile("index", BRIEF);
    Profile ONE_LINE = new Profile("index", LIST_ENTRY);

    Profile EDIT = new Profile("edit", CONTENT);
    Profile EDIT_BRIEF = new Profile("edit-brief", EDIT);
    Profile EDIT_LIST_ENTRY = new Profile("", EDIT_BRIEF);
    Profile EDIT_ONE_LINE = new Profile("", EDIT_LIST_ENTRY);

    Profile INDEX = new Profile("index", CONTENT);

}
