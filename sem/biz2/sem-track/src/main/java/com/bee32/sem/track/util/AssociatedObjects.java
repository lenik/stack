package com.bee32.sem.track.util;

import java.util.List;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.inventory.entity.StockOrder;

public class AssociatedObjects implements ITypeAbbrAware{

    public static Class<Chance>salesChance = Chance.class;
    public static Class<?> order = StockOrder.class;

    public static List<Class<?>> getIssueAssociatedList(){
        //TODO java reflection get fields;

        return null;
    }

}
