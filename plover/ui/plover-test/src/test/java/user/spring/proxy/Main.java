package user.spring.proxy;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;

@Import(ScanTestxContext.class)
@Lazy
public class Main {

    @Bean
    LazyInitTargetSource targetAddress() {
        LazyInitTargetSource targetSource = new LazyInitTargetSource();
        targetSource.setTargetBeanName("ccc");
        return targetSource;
    }

    @Bean
    LazyInitTargetSource targetAddress2() {
        LazyInitTargetSource targetSource = new LazyInitTargetSource();
        targetSource.setTargetBeanName("ccc");
        return targetSource;
    }

    @Inject
    @Named("targetAddress2")
    LazyInitTargetSource targetAddress1;

    @Bean
    ProxyFactoryBean proxyAddress()
            throws ClassNotFoundException {

        System.out.println("Create proxy Address");

        targetAddress1.setTargetBeanName("concreteAddress");

        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTargetSource(targetAddress1);

        Class<?>[] ifaces = new Class<?>[] { IAddress.class };
        factoryBean.setProxyInterfaces(ifaces);
        // factoryBean.setProxyTargetClass(proxyTargetClass)
        return factoryBean;
    }

    @Bean
    @Lazy
    Object concreteAddress() {
        System.out.println("Create concrete Address");
        Address address = new Address("Concrete street");
        return address;
    }

    public static void main(String args[]) {

        ApplicationContext context = ApplicationContextBuilder.buildSelfHostedContext(Main.class);

        System.out.println("After initialization");

        Employee employee = context.getBean(Employee.class);
        System.out.println(employee);
        System.out.println("Street: " + employee.getAddress().getStreet());
    }

}
