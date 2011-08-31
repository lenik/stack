package user.hibernate.fea2;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(Fea2Unit.class)
public class PlayByDaoTestCase
        extends WiredDaoTestCase {

    @Inject
    FoodFeat player;

    @Test
    public void testMain() {
        player.prepare();
        player.listFood();
    }

}
