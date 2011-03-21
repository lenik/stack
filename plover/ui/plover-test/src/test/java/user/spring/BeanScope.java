package user.spring;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;

public class BeanScope
        extends WiredTestCase {

    @Test
    public void getFast2() {
        ShortLife s1 = application.getBean(ShortLife.class);
        ShortLife s2 = application.getBean(ShortLife.class);
        assertSame(s1, s2);
    }

    @Test
    public void getSlow2()
            throws InterruptedException {
        ShortLife s1 = application.getBean(ShortLife.class);
        Thread.sleep(1001);
        ShortLife s2 = application.getBean(ShortLife.class);
        assertNotSame(s1, s2);
    }

}
