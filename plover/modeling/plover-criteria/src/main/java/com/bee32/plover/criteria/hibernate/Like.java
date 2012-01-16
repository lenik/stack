package com.bee32.plover.criteria.hibernate;

import javax.free.UnexpectedException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class Like
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final boolean ignoreCase;
    final String pattern;
    final MatchMode matchMode;

    static final int EXACT = 0;
    static final int END = 1;
    static final int START = 2;
    static final int ANYWHERE = 3;
    final int _mode;

    public Like(String propertyName, String pattern) {
        this(false, propertyName, pattern, null);
    }

    public Like(String propertyName, String pattern, MatchMode matchMode) {
        this(false, propertyName, pattern, matchMode);
    }

    public Like(boolean ignoreCase, String propertyName, String pattern) {
        this(ignoreCase, propertyName, pattern, null);
    }

    public Like(boolean ignoreCase, String propertyName, String pattern, MatchMode matchMode) {
        super(propertyName);

        if (pattern == null)
            throw new NullPointerException("pattern");

        this.ignoreCase = ignoreCase;
        if (ignoreCase)
            pattern = pattern.toLowerCase();

        int mode = 0;
        if (matchMode == null) { // c14n.
            if (pattern.startsWith("%"))
                mode |= 1;
            if (pattern.endsWith("%"))
                mode |= 2;

            switch (mode) {
            case 0:
                matchMode = MatchMode.EXACT;
                break;
            case 1: // %pattern
                matchMode = MatchMode.END;
                pattern = pattern.substring(1);
                break;
            case 2: // pattern%
                matchMode = MatchMode.START;
                pattern = pattern.substring(0, pattern.length() - 1);
                break;
            case 3: // %pattern%
                matchMode = MatchMode.ANYWHERE;
                pattern = pattern.substring(1, pattern.length() - 1);
                break;
            default:
                assert false;
            }
        } else {
            if (matchMode == MatchMode.EXACT)
                mode = EXACT;
            else if (matchMode == MatchMode.END)
                mode = END;
            else if (matchMode == MatchMode.START)
                mode = START;
            else if (matchMode == MatchMode.ANYWHERE)
                mode = ANYWHERE;
        }

        this.pattern = pattern;
        this.matchMode = matchMode;
        this._mode = mode;
    }

    @Override
    protected Criterion buildCriterion(int options) {
        if (matchMode == null)
            return Restrictions.like(propertyName, pattern);
        else
            return Restrictions.like(propertyName, pattern, matchMode);
    }

    @Override
    protected boolean filterValue(Object val) {
        if (val == null)
            return false;
        String s = val.toString();

        if (ignoreCase)
            s = s.toLowerCase();

        switch (_mode) {
        case EXACT: // pattern
            return s.equals(pattern);

        case END: // %pattern
            return s.endsWith(pattern);

        case START: // pattern%
            return s.startsWith(pattern);

        case ANYWHERE: // %pattern%
            return s.indexOf(pattern) != -1;

        default:
            throw new UnexpectedException();
        }
    }

    @Override
    public String getOperator() {
        return "LIKE";
    }

    @Override
    public String getOperatorName() {
        StringBuilder sb = new StringBuilder(30);
        switch (_mode) {
        case START:
            sb.append("开头为");
            break;
        case END:
            sb.append("结尾为");
            break;
        case EXACT:
            sb.append("匹配");
            break;
        case ANYWHERE:
            sb.append("含有");
            break;
        default:
            throw new UnexpectedException();
        }
        if (ignoreCase)
            sb.append(" （不区分大小写）");
        return sb.toString();
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append("'");

        if (_mode == END || _mode == ANYWHERE)
            out.append("%");

        String patternEscaped = pattern;
        patternEscaped = patternEscaped.replace("\\", "\\\\");
        patternEscaped = patternEscaped.replace("\"", "\\\"");
        patternEscaped = patternEscaped.replace("\'", "\\\'");
        out.append(patternEscaped);

        if (_mode == START || _mode == ANYWHERE)
            out.append("%");

        out.append("'");
    }

}
