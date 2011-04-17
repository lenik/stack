package com.bee32.sem.process.verify.builtin.web;

import java.util.Map;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;

import com.bee32.plover.arch.util.DataExchangeObject;

public class DataTableDxo
        extends DataExchangeObject<Void> {

    private static final long serialVersionUID = 1L;

    /**
     * int iDisplayStart Display start point
     */
    int displayStart;

    /**
     * int iDisplayLength Number of records to display
     */
    int displayLength;

    /**
     * int iColumns Number of columns being displayed (useful for getting individual column search
     * info)
     */
    int columnCount;

    /**
     * string sSearch Global search field
     */
    String search;

    /**
     * boolean bEscapeRegex Global search is regex or not
     */
    boolean escapeRegex;

    /**
     * int iSortingCols Number of columns to sort on
     */
    int sortingCols;

    static class ColumnOption {

        /**
         * boolean bSortable_(int) Indicator for if a column is flagged as sortable or not on the
         * client-side
         */
        boolean sortable;

        /**
         * boolean bSearchable_(int) Indicator for if a column is flagged as searchable or not on
         * the client-side
         */
        boolean searchable;

        /**
         * string sSearch_(int) Individual column filter
         */
        String search;

        /**
         * boolean bEscapeRegex_(int) Individual column filter is regex or not
         */
        boolean escapeRegex;

        /**
         * int iSortCol_(int) Column being sorted on (you will need to decode this number for your
         * database)
         */
        int sortCol;

        /**
         * string sSortDir_(int) Direction to be sorted - "desc" or "asc". Note that the prefix for
         * this variable is wrong in 1.5.x where iSortDir_(int) was used)
         */
        String sortDir;

    }

    ColumnOption[] columnOptions;

    // REQUEST & REPLY

    /**
     * Used in both request & reply.
     *
     * string sEcho Information for DataTables to use for rendering
     *
     * <p>
     * string sEcho An unaltered copy of sEcho sent from the client side. This parameter will change
     * with each draw (it is basically a draw count) - so it is important that this is implemented.
     * Note that it strongly recommended for security reasons that you 'cast' this parameter to an
     * integer in order to prevent Cross Site Scripting (XSS) attacks.
     */
    String echo;

    // ** REPLY-ONLY **
    /**
     * int iTotalRecords Total records, before filtering (i.e. the total number of records in the
     * database)
     */
    Integer totalRecords;

    /**
     * int iTotalDisplayRecords Total records, after filtering (i.e. the total number of records
     * after filtering has been applied - not just the number of records being returned in this
     * result set)
     */
    Integer totalDisplayRecords;

    /**
     * string sColumns Optional - this is a string of column names, comma separated (used in
     * combination with sName) which will allow DataTables to reorder data on the client-side if
     * required for display
     */
    String columns;

    /**
     * array array mixed aaData The data in a 2D array
     */
    Object data;

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException {
        displayStart = map.getInt("iDisplayStart");
        displayLength = map.getInt("iDisplayLength");
        columnCount = map.getInt("iColumns");
        search = map.getString("sSearch");
        escapeRegex = map.getBoolean("bEscapeRegex");
        sortingCols = map.getInt("iSortingCols");

        echo = map.getString("sEcho");
        if (echo != null) {
            try {
                int echoInt = Integer.parseInt(echo);
                echo = String.valueOf(echoInt);
            } catch (NumberFormatException e) {
                echo = "Echo isn't integer, XSS-Attack maybe?";
            }
        }
    }

    @Override
    public void export(Map<String, Object> map) {
        if (totalRecords != null)
            map.put("iTotalRecords", totalRecords);
        if (totalDisplayRecords != null)
            map.put("iTotalDisplayRecords", totalDisplayRecords);
        if (echo != null)
            map.put("sEcho", echo);
        if (columns != null)
            map.put("sColumns", columns);
        if (data != null)
            map.put("aaData", data);
    }

}
