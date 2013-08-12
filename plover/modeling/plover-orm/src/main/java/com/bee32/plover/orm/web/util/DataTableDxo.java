package com.bee32.plover.orm.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.free.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.DataExchangeObject;

/**
 * jQuery DataTable DXO 对象
 */
public class DataTableDxo
        extends DataExchangeObject<Void> {

    private static final long serialVersionUID = 1L;

    /**
     * int iDisplayStart Display start point
     */
    public int displayStart;

    /**
     * int iDisplayLength Number of records to display
     */
    public int displayLength;

    /**
     * int iColumns Number of columns being displayed (useful for getting individual column search
     * info)
     */
    public int columnCount;

    /**
     * string sSearch Global search field
     */
    public String search;

    /**
     * boolean bEscapeRegex Global search is regex or not
     */
    public boolean escapeRegex;

    /**
     * int iSortingCols Number of columns to sort on
     */
    int sortingCols;

    static class ColumnOption {

        /**
         * boolean bSortable_(int) Indicator for if a column is flagged as sortable or not on the
         * client-side
         */
        public boolean sortable;

        /**
         * boolean bSearchable_(int) Indicator for if a column is flagged as searchable or not on
         * the client-side
         */
        public boolean searchable;

        /**
         * string sSearch_(int) Individual column filter
         */
        public String search;

        /**
         * boolean bEscapeRegex_(int) Individual column filter is regex or not
         */
        public boolean escapeRegex;

        /**
         * int iSortCol_(int) Column being sorted on (you will need to decode this number for your
         * database)
         */
        public int sortCol;

        /**
         * string sSortDir_(int) Direction to be sorted - "desc" or "asc". Note that the prefix for
         * this variable is wrong in 1.5.x where iSortDir_(int) was used)
         */
        public String sortDir;

    }

    public ColumnOption[] columnOptions;

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
    public String echo;

    // ** REPLY-ONLY **
    /**
     * int iTotalRecords Total records, before filtering (i.e. the total number of records in the
     * database)
     */
    public Integer totalRecords;

    /**
     * int iTotalDisplayRecords Total records, after filtering (i.e. the total number of records
     * after filtering has been applied - not just the number of records being returned in this
     * result set)
     */
    public Integer totalDisplayRecords;

    /**
     * string sColumns Optional - this is a string of column names, comma separated (used in
     * combination with sName) which will allow DataTables to reorder data on the client-side if
     * required for display
     */
    public String columns;

    /**
     * array array mixed aaData The data in a 2D array
     */
    public Object data;

    transient List<List<Object>> _rows;
    transient Object[] _row;
    transient int _index;

    public DataTableDxo() {
    }

    public DataTableDxo(HttpServletRequest request)
            throws ServletException {
        parse(request);
    }

    /**
     * Append an object to the current row.
     *
     * @param val
     *            The object to be appended.
     * @return This object.
     */
    public synchronized DataTableDxo push(Object val) {
        if (_row == null) {
            _row = new Object[columnCount];
            _index = 0;
        }
        _row[_index++] = val;
        return this;
    }

    /**
     * Commit the current row. So the next call to {@link #push(Object)} will append to a new row.
     *
     * @return This object.
     */
    public synchronized DataTableDxo next() {
        if (_rows == null)
            _rows = new ArrayList<List<Object>>();
        _rows.add(Arrays.asList(_row));
        _row = null;
        return this;
    }

    /**
     * Done with the table data. This will compose all shifted out rows to a 2-dimension array,
     * which you can access it by {@link #data} field.
     *
     * @return This object.
     */
    public synchronized DataTableDxo commit() {
        if (_rows == null)
            _rows = new ArrayList<List<Object>>();
        data = _rows;
        return this;
    }

    @Override
    protected void _parse(TextMap map)
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
    protected void _export(Map<String, Object> map) {
        if (totalRecords != null)
            map.put("iTotalRecords", totalRecords);

        if (totalDisplayRecords != null)
            map.put("iTotalDisplayRecords", totalDisplayRecords);

        if (echo != null)
            map.put("sEcho", echo);

        if (columns != null)
            map.put("sColumns", columns);

        commit();
        if (data != null)
            map.put("aaData", data);
    }

}
