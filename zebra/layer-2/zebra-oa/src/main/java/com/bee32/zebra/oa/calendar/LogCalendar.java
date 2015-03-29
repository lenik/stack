package com.bee32.zebra.oa.calendar;

import java.util.Calendar;

import net.bodz.bas.c.string.StringPred;
import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 日历
 */
@ObjectType(LogSelector.class)
public class LogCalendar
        extends QuickIndex {

    public LogCalendar(IQueryable context) {
        super(context);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        int dash1 = token.indexOf('-');
        if (dash1 == -1)
            return null;

        String _year = token.substring(0, dash1);
        if (!StringPred.isDecimal(_year))
            return null;
        int year = Integer.parseInt(_year);

        token = token.substring(dash1 + 1);
        if (token.isEmpty())
            return null;

        char type = token.charAt(0);
        if (type >= '0' && type <= '9')
            type = 'm';
        else
            token = token.substring(1);

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR, year);

        LogSelector range = new LogSelector();
        switch (type) {
        case 'm':
            if (!StringPred.isDecimal(token))
                return null;
            int month = Integer.parseInt(token);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            range.setView(LogSelector.MONTH);
            range.setStart(calendar.getTime());
            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, maxDay);
            range.setEnd(calendar.getTime());
            break;

        case 'w':
            if (!StringPred.isDecimal(token))
                return null;
            int week = Integer.parseInt(token);
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            range.setView(LogSelector.WEEK);
            range.setStart(calendar.getTime());
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            range.setEnd(calendar.getTime());
            break;

        default:
            return null;
        }

        return PathArrival.shift(previous, range, tokens);
    }

}
