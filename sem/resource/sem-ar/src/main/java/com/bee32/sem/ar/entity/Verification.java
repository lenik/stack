package com.bee32.sem.ar.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.process.base.ProcessEntity;

/**
 * 核销
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "verification_seq", allocationSize = 1)
public class Verification extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Receivable receivable;
    Received received;
    BigDecimal amount;  //应收和已收的核销金额
}
