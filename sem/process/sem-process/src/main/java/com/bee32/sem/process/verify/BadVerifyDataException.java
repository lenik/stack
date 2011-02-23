package com.bee32.sem.process.verify;

/**
 * 传入的 {@link IVerifyState} 数据格式不正确，或者对调用的 {@link IVerifyPolicy} 策略不适用。
 */
public class BadVerifyDataException
        extends VerifyException {

    private static final long serialVersionUID = 1L;

    private final Class<? extends VerifyState> expectedVerifyStateType;
    private VerifyState verifyState;

    public BadVerifyDataException(String message, Class<? extends VerifyState> expectedVerifyStateType,
            VerifyState verifyState) {
        super(message);
        this.expectedVerifyStateType = expectedVerifyStateType;
        this.verifyState = verifyState;
    }

    public BadVerifyDataException(Class<? extends VerifyState> expectedVerifyDataType, VerifyState verifyState) {
        this("Incorrent verify data type: " + expectedVerifyDataType.getName() + ", state=" + verifyState, //
                expectedVerifyDataType, verifyState);
    }

    public Class<? extends VerifyState> getExpectedVerifyDataType() {
        return expectedVerifyStateType;
    }

    public VerifyState getVerifyState() {
        return verifyState;
    }

}
