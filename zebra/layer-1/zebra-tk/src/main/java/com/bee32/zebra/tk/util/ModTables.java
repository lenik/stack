package com.bee32.zebra.tk.util;

import org.json.JSONWriter;

import net.bodz.bas.html.io.mod.table.DataRow;
import net.bodz.bas.html.io.mod.table.ModTable;

public class ModTables {

    public static void toJson(ModTable table, JSONWriter out) {
        out.object();

        DataRow headRow = table.getHeadRow();
        if (!headRow.isEmpty()) {
            out.key("thead");
            toJson(headRow, out);
        }

        DataRow footRow = table.getFootRow();
        if (!footRow.isEmpty()) {
            out.key("tfoot");
            toJson(footRow, out);
        }

        out.key("tbody");
        out.array();
        for (DataRow row : table.getRows()) {
            toJson(row, out);
        }
        out.endArray();

        out.endObject();
    }

    public static void toJson(DataRow row, JSONWriter out) {
        out.array();
        for (StringBuilder cell : row) {
            String s = cell.toString();
            out.value(s);
        }
        out.endArray();
    }

}
