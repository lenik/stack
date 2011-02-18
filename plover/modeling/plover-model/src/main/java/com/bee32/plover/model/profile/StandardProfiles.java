package com.bee32.plover.model.profile;

public interface StandardProfiles {

    Profile SUMMARY = new Profile("summary");
    Profile ONE_LINE = new Profile("one-line", SUMMARY);
    Profile LIST_ENTRY = new Profile("index", ONE_LINE);

    Profile DUMP = new Profile("dump");
    Profile VIEW = new Profile("page", DUMP);
    Profile SHORT = new Profile("short", VIEW);
    Profile WAP = new Profile("wap", SHORT);

    Profile EDIT = new Profile("edit");
    Profile EDIT_LIST_ENTRY = new Profile("edit-list-entry", EDIT);

}
