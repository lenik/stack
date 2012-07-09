package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收票据
 */
@Entity
@DiscriminatorValue("RNOTE")
public class NoteReceivable
        extends Note {

    private static final long serialVersionUID = 1L;

}
