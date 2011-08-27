package com.bee32.plover.ox1.xp;

import java.util.List;

public interface IXPoolable<X extends XPool<?>> {

    List<X> pool();

}
