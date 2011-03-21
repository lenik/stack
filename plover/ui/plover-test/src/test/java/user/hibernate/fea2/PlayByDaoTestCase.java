package user.hibernate.fea2;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.orm.util.WiredDaoTestCase;

@ContextConfiguration("context.xml")
public class PlayByDaoTestCase
        extends WiredDaoTestCase {

    @Inject
    FeaturePlayer player;

    public PlayByDaoTestCase() {
        super(FeatureSFB.unit);
    }

    @Test
    public void testMain() {
        player.prepare();
        player.listFood();
    }

}
