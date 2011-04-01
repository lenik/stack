package user.hibernate.fea2;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.orm.unit.UseUnit;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@UseUnit(Fea2Unit.class)
@ContextConfiguration("context.xml")
public class PlayByDaoTestCase
        extends WiredDaoTestCase {

    @Inject
    FeaturePlayer player;

    @Test
    public void testMain() {
        player.prepare();
        player.listFood();
    }

}
