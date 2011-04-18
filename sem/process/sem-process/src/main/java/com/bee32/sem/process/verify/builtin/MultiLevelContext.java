package com.bee32.sem.process.verify.builtin;

import com.bee32.sem.process.verify.AllowedByContext;

/**
 * 表示业务对象具有可供多级审核策略使用的关键值。
 */
public class MultiLevelContext
        extends AllowedByContext
        implements IMultiLevelContext {

    private String valueDescription;
    private long longValue;

    @Override
    public String getValueDescription() {
        return valueDescription;
    }

    public void setValueDescription(String valueDescription) {
        this.valueDescription = valueDescription;
    }

    @Override
    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

}
