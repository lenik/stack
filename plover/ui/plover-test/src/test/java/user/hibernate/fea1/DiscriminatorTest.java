package user.hibernate.fea1;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.p6spy.engine.common.P6SpyOptions;

@ImportUnit(ColorSystem.class)
public class DiscriminatorTest
        extends WiredDaoTestCase {

    @Test
    @Ignore
    public void listAllColors() {
        HibernateTemplate template = getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<Color> list = template.loadAll(Color.class);
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    @Ignore
    public void listCMYKOnly() {
        HibernateTemplate template = getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<CMYK> list = template.loadAll(CMYK.class);
        assertEquals(2, list.size());
    }

    @Test
    public void listRGBBased() {
        String inc = P6SpyOptions.getIncludecategories();
        System.err.println("[" + inc + "]");

        HibernateTemplate template = getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<RGB> list = template.loadAll(RGB.class);
        for (RGB rgb : list)
            System.err.println("RGB: " + rgb);
    }

}
