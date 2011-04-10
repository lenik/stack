package user.hibernate.fea2.ext;

import com.bee32.plover.orm.entity.EntityDao;

public class FoodDao
        extends EntityDao<Food, Integer> {

    public FoodDao() {
        super(Food.class, Integer.class);
    }

}
