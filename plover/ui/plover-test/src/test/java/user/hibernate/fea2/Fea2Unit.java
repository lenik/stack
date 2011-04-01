package user.hibernate.fea2;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.Food;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class Fea2Unit
        extends PersistenceUnit {

    protected void preamble() {
        add(Food.class);
        add(Fruit.class);
        add(Banana.class);

        add(Friend.class);
    }

}
