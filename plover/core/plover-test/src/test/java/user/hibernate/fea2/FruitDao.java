package user.hibernate.fea2;

import com.bee32.plover.orm.entity.AbstractDao;

public class FruitDao
        extends AbstractDao<Fruit, Integer> {

    public FruitDao() {
        super(Fruit.class, Integer.class);
    }

}
