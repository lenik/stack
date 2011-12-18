package com.bee32.sem.process.verify.dto;

import com.bee32.sem.process.verify.IVerifiable;

/**
 * @see IVerifiable
 */
public interface IVerifiableDto {

    /**
     * @see IVerifiable#getVerifyContext()
     */
    VerifyContextDto<?> getVerifyContext();

}
