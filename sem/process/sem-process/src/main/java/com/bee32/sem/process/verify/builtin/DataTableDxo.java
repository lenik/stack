package com.bee32.sem.process.verify.builtin;

import java.util.Map;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;

import com.bee32.plover.arch.util.DataExchangeObject;

public class DataTableDxo
        extends DataExchangeObject<Void> {

    private static final long serialVersionUID = 1L;

    long displayStart;
    long displayLength;
    int columns;
    int echo;

    long totalRecords;
    long totalDisplayRecords;

    Object data;

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException {
        displayStart = map.getLong("iDisplayStart");
        displayLength = map.getLong("iDisplayLength");
        columns = map.getInt("iColumns");
        echo = map.getInt("sEcho");
    }

    public void export(Map<String, Object> map) {
        // map.put("iDisplayStart", displayStart);
        // map.put("iDisplayLength", displayStart);
        // map.put("iColumns", columns);
        map.put("sEcho", echo);

        map.put("iTotalRecords", totalRecords);
        map.put("iTotalDisplayRecords", totalDisplayRecords);
        map.put("aaData", data);
    }

}
