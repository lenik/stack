package user.spring;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;

@Import(ScanTestxContext.class)
public class SpringLazyTest
        extends Assert {

    @Inject
    BigBlob blob;

    public SpringLazyTest() {
        ApplicationContextBuilder.selfWire(this);
    }

    @Test
    public void test1() {
        System.out.println("test method");
        System.out.println("blob: " + blob);
    }

}
