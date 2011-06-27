package user.hibernate.fea1;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(ColorSystem.class)
public class DiscriminatorTest
        extends WiredDaoTestCase {

    @Inject
    CommonDataManager dataManager;

    @Test
    public void listAllColors() {
        IEntityAccessService<Color, String> colorDao = dataManager.access(Color.class);

        for (Color color : ColorSystem.getPredefinedColors())
            colorDao.save(color);

        List<Color> list = colorDao.list();
        // for (Color c : list)
        // System.out.println("Color: " + c);
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    public void listCMYKOnly() {
        IEntityAccessService<Color, String> colorDao = dataManager.access(Color.class);
        IEntityAccessService<CMYK, String> cmykDao = dataManager.access(CMYK.class);

        for (Color color : ColorSystem.getPredefinedColors())
            colorDao.save(color);

        List<CMYK> list = cmykDao.list();
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    public void listRGBBased() {
        IEntityAccessService<Color, String> colorDso = dataManager.access(Color.class);
        IEntityAccessService<RGB, String> rgbDao = dataManager.access(RGB.class);
        for (Color color : ColorSystem.getPredefinedColors())
            colorDso.save(color);

        List<RGB> list = rgbDao.list();
        // for (RGB rgb : list)
        // System.err.println("RGB: " + rgb);
        RGB cyan = new RGB("CYAN", java.awt.Color.CYAN);
        list.contains(cyan);
    }

}
