package user.hibernate.fea1;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(ColorSystem.class)
public class DiscriminatorTest
        extends WiredDaoTestCase {

    @Inject
    CommonDataManager dataManager;

    @Test
    public void listAllColors() {
        for (Color color : ColorSystem.getPredefinedColors())
            dataManager.save(color);

        List<Color> list = dataManager.loadAll(Color.class);
        // for (Color c : list)
        // System.out.println("Color: " + c);
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    public void listCMYKOnly() {
        for (Color color : ColorSystem.getPredefinedColors())
            dataManager.save(color);

        List<CMYK> list = dataManager.loadAll(CMYK.class);
        assertTrue(list.contains(ColorSystem.black));
    }

    @Test
    public void listRGBBased() {
        for (Color color : ColorSystem.getPredefinedColors())
            dataManager.save(color);

        List<RGB> list = dataManager.loadAll(RGB.class);
        // for (RGB rgb : list)
        // System.err.println("RGB: " + rgb);
        RGB cyan = new RGB("CYAN", java.awt.Color.CYAN);
        list.contains(cyan);
    }

}
