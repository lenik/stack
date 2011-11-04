package com.bee32.plover.orm.entity;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.bee32.plover.site.scope.PerSite;

@Service
@PerSite
public class PlainEasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapper<E, K> {

}
