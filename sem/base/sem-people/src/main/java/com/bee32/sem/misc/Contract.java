package com.bee32.sem.misc;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 合同
 *
 * <p lang="en">
 * Contract
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "contract_seq", allocationSize = 1)
public abstract class Contract
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

}
