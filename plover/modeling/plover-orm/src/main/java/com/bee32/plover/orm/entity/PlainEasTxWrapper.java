package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class PlainEasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapper<E, K> {

}
