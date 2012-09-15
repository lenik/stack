package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * For Embedded.
 */
@MappedSuperclass
public abstract class EmbeddablePiece
        implements Serializable {

    private static final long serialVersionUID = 1L;

}
