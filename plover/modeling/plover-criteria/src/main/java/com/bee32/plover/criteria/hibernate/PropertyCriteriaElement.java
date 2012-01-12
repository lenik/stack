package com.bee32.plover.criteria.hibernate;

import org.springframework.expression.Expression;

public abstract class PropertyCriteriaElement
        extends SpelCriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;

    public PropertyCriteriaElement(String propertyName) {
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        this.propertyName = propertyName;
    }

    @Override
    protected Expression compile() {
        return compile(propertyName);
    }

    public abstract String getOperator();

    public abstract String getOperatorName();

    protected abstract void formatValue(StringBuilder out);

    @Override
    public void format(StringBuilder out) {
        out.append("(test ");
        out.append(propertyName);
        out.append(" ");
        out.append(getOperator());
        out.append(" ");
        formatValue(out);
        out.append(")");
    }

    protected String quoteValue(Object val) {
        if (val == null)
            return "NULL";
        if (val instanceof String) {
            String str = (String) val;
            str = str.replace("\\", "\\\\");
            str = str.replace("\'", "\\\'");
            str = str.replace("\"", "\\\"");
            return "'" + str + "'";
        }
        return val.toString();
    }

}
