package user.hibernate.fea2.ext;

import com.bee32.plover.orm.entity.AbstractDao;

public class BananaDao
        extends AbstractDao<Banana, Integer> {

    public BananaDao() {
        super(Banana.class, Integer.class);
    }

}
