package user.spring;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.test.WiredTestCase;

@ContextConfiguration("context1.xml")
@Ignore
public class CreateOrder
        extends WiredTestCase
        implements InitializingBean {

    @Inject
    BigBlob blob;

    public CreateOrder() {
        System.out.println("Ctor");
    }

    {
        System.out.println("Object init code");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("After properties set");
    }

    @Test
    public void create() {
        System.out.println("Test method");
        System.out.println("Blob: " + blob);
    }

}
