package com.bee32.sem.process.verify.builtin;

import com.bee32.sem.process.verify.IAllowedByContext;

/**
 * 表示业务对象具有可供多级审核策略使用的关键值。
 */
public interface IMultiLevelContext
        extends IAllowedByContext {

    String getValueDescription();

    long getLongValue();

}
