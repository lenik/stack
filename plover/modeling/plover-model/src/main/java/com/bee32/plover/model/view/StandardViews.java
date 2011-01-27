package com.bee32.plover.model.view;

public interface StandardViews {

    View SUMMARY = new View("summary");
    View ONE_LINE = new View("one-line", SUMMARY);
    View LIST_ENTRY = new View("index", ONE_LINE);

    View DUMP = new View("dump");
    View VIEW = new View("page", DUMP);
    View SHORT = new View("short", VIEW);
    View WAP = new View("wap", SHORT);

    View EDIT = new View("edit");
    View EDIT_LIST_ENTRY = new View("edit-list-entry", EDIT);

}
