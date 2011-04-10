package user.hibernate.fea2.ext;

import com.bee32.plover.orm.entity.EntityDao;

public class BananaDao
        extends EntityDao<Banana, Integer> {

    public BananaDao() {
        super(Banana.class, Integer.class);
    }

}
