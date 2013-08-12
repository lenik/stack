package com.bee32.icsf.principal;

/**
 * 主体检验异常
 *
 * 安全主体的内部构成存在问题，可能是存在引用回路，也可能是数据不一致导致。
 */
public class PrincipalCheckException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PrincipalCheckException() {
        super();
    }

    public PrincipalCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrincipalCheckException(String message) {
        super(message);
    }

    public PrincipalCheckException(Throwable cause) {
        super(cause);
    }

}
