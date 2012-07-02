package com.bee32.sem.ar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收票据
 *
 */
@Entity
@DiscriminatorValue("NR")
public class NotesReceivable extends Received {
    private static final long serialVersionUID = 1L;

    String billNo;  //票据号
    BillType billType;    //票据类型
}
