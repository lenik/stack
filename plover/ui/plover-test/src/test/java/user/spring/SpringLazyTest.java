package user.spring;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("lazy-context.xml")
public class SpringLazyTest
        extends Assert {

    @Inject
    BigBlob blob;

    @Test
    public void test1() {
        System.out.println("test method");
        System.out.println("blob: " + blob);
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("lazy-context.xml", SpringLazyTest.class);

        AutowireCapableBeanFactory acbf = context.getAutowireCapableBeanFactory();

        SpringLazyTest bean = acbf.createBean(SpringLazyTest.class);

        bean.test1();
    }

}
