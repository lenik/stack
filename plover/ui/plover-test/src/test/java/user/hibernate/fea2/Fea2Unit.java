package user.hibernate.fea2;

import com.bee32.plover.orm.unit.PersistenceUnit;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.Food;

public class Fea2Unit
        extends PersistenceUnit {

    protected void preamble() {
        add(Food.class);
        add(Fruit.class);
        add(Banana.class);

        add(Friend.class);
    }

}
