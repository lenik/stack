package com.bee32.sem.ar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收单
 *
 */
@Entity
@DiscriminatorValue("ABLE")
public class Receivable extends ARBase {

    private static final long serialVersionUID = 1L;




}
