package com.bee32.plover.orm.feaCat;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;

/**
 * 动物（测试）数据单元
 *
 * Animal (For Test) Unit
 */
@ImportUnit(PloverORMUnit.class)
public class AnimalUnit
        extends PersistenceUnit {

    protected void preamble() {
        add(Cat.class);
        add(Tiger.class);
        add(CatFavTag.class);
    }

    static final AnimalUnit instance = new AnimalUnit();

    public static AnimalUnit getInstance() {
        return instance;
    }

}
