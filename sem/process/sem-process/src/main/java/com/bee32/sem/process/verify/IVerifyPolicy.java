package com.bee32.sem.process.verify;

import java.io.Serializable;

import javax.free.DecodeException;
import javax.free.EncodeException;

import com.bee32.plover.model.IModel;

/**
 * 审核策略用于检验业务实体是否被审核，但不对业务的具体审核行为作出规定。即策略用于“只读”、“判断”的目的；而不是对业务执行”审核“动作、并形成审核结果的”写入“目的。
 */
public interface IVerifyPolicy<C, S extends VerifyState>
        extends IModel, Serializable {

    /**
     * 检验业务实体是否已被审核。 如果未审核或审核失败，抛出 {@link VerifyException} 异常。
     *
     * @throws VerifyException
     *             如果未被审核
     * @param state
     *            审核状态。可以为 <code>null</code>。
     */
    void verify(C context, S state)
            throws VerifyException;

    /**
     * 判断业务实体是否已被审核，如果已审核返回 <code>true</code>。
     *
     * 等效于：
     *
     * <pre>
     * try {
     *     verify(verifyObject);
     *     return true;
     * } catch (BadVerifyDataException e) {
     *     throw e;
     * } catch (VerifyException e) {
     *     return false;
     * }
     * </pre>
     *
     * @param state
     *            审核状态。可以为 <code>null</code>。
     */
    boolean isVerified(C context, S state);

    /**
     * 将审核状态编码为特殊格式的文本。
     *
     * @param state
     *            审核状态，可以为 <code>null</code>。
     * @return 特殊格式的文本。可以为 <code>null</code>。
     */
    String encodeState(S state)
            throws EncodeException;

    /**
     * 将已编码的审核状态还原为对象。
     *
     * @param stateClob
     *            编码了的审核状态文本。可以为 <code>null</code>。
     * @return 审核状态对象。可以为 <code>null</code>。
     */
    S decodeState(String stateClob)
            throws DecodeException;

}
