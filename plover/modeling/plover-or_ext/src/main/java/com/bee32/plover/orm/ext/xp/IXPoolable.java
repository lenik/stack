package com.bee32.plover.orm.ext.xp;

import java.util.List;

public interface IXPoolable<X extends XPool<?>> {

    List<X> pool();

}
