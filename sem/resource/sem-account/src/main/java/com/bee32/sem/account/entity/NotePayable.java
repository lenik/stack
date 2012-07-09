package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应付票据
 */
@Entity
@DiscriminatorValue("PNOTE")
public class NotePayable
        extends Note {

    private static final long serialVersionUID = 1L;

}
