package com.bee32.sem.process.verify.builtin.dto;

import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.util.VerifyContextDto;

/**
 * @see IVerifiable
 */
public interface IVerifiableDto {

    /**
     * @see IVerifiable#getVerifyContext()
     */
    VerifyContextDto<?> getVerifyContext();

}
