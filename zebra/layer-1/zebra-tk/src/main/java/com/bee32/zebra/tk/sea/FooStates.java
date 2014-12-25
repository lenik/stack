package com.bee32.zebra.tk.sea;

import net.bodz.bas.repr.state.IStateConsts;
import net.bodz.bas.repr.state.State;
import net.bodz.bas.repr.state.StateType;

public class FooStates
        implements IStateConsts {

    public static final int ID_PROCESS = 102;
    public static final int ID_OK = 200;
    public static final int ID_FAIL = 417;
    public static final int ID_INVALID = 400;
    public static final int ID_TIMEOUT = 408;

    /**
     * HTTP 102 Processing
     * 
     * @label.zh.cn 处理中
     */
    public static final State PROCESS = new State(ID_PROCESS, "process", StateType.ACCEPTED);

    /**
     * HTTP 200 OK
     * 
     * @label.zh.cn 成功
     */
    public static final State OK = new State(ID_OK, "ok", StateType.ACCEPTED);

    /**
     * HTTP 417 Expectation Failed
     * 
     * @label.zh.cn 作废
     */
    public static final State FAIL = new State(ID_FAIL, "fail", StateType.ACCEPTED);

    /**
     * HTTP 400 Bad Request
     * 
     * @label.zh.cn 无效
     */
    public static final State INVALID = new State(ID_INVALID, "invalid", StateType.ERROR);

    /**
     * HTTP 408 Request Timeout
     * 
     * @label.zh.cn 无应答
     */
    public static final State TIMEOUT = new State(ID_TIMEOUT, "timeout", StateType.ERROR);

}
