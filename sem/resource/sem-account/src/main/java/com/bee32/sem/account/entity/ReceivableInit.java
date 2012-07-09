package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收初始化
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("RINIT")
public class ReceivableInit extends Receivable {

    private static final long serialVersionUID = 1L;

}
