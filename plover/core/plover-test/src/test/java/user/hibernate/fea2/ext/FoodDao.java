package user.hibernate.fea2.ext;

import com.bee32.plover.orm.entity.AbstractDao;

public class FoodDao
        extends AbstractDao<Food, Integer> {

    public FoodDao() {
        super(Food.class, Integer.class);
    }

}
