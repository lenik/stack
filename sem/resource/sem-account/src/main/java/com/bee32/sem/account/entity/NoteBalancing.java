package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.process.base.ProcessEntity;

/**
 * 票据三总结算方式的基类:贴现，背书，结算
 * @author jack
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "note_balancing_seq", allocationSize = 1)
public class NoteBalancing extends ProcessEntity {

}
