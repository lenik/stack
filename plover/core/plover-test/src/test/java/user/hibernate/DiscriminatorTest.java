package user.hibernate;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.bee32.plover.orm.dao.HibernateTemplate;
import com.bee32.plover.orm.util.hibernate.HibernateLibrary;

public class DiscriminatorTest
        extends HibernateLibrary {

    public DiscriminatorTest() {
        super(ColorSystem.unit);
    }

    @Test
    public void listCMYKOnly() {
        HibernateTemplate template = getHibernateTemplate();

        for (Color color : ColorSystem.getPredefinedColors())
            template.save(color);

        List<CMYK> list = template.loadAll(CMYK.class);
        assertEquals(2, list.size());
    }

}
