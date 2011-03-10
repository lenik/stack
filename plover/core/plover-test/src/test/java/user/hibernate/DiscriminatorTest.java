package user.hibernate;

import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.orm.PloverOrmModule;
import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.util.HibernateConfigurer;
import com.bee32.plover.orm.util.HibernateUnitConfigurer;
import com.bee32.plover.test.WiredAssembledTestCase;

public class DiscriminatorTest
        extends WiredAssembledTestCase {

    PloverOrmModule pom;

    @Inject
    HibernateConfigurer hibernateConfigurer;

    HibernateUnitConfigurer hl;

    public DiscriminatorTest() {
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        hl = new HibernateUnitConfigurer(hibernateConfigurer, ColorSystem.unit);
    }

    @Test
    public void listAllColors() {
        HibernateTemplate template = hl.getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<Color> list = template.loadAll(Color.class);
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    @Ignore
    public void listCMYKOnly() {
        HibernateTemplate template = hl.getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<CMYK> list = template.loadAll(CMYK.class);
        assertEquals(2, list.size());
    }

}
