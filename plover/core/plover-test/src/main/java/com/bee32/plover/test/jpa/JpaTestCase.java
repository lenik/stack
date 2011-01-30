package com.bee32.plover.test.jpa;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class JpaTestCase {

    protected final Injector injector;

    public JpaTestCase() {
        Module providerModule = getProviderModule();
        injector = Guice.createInjector(providerModule);
    }

    /**
     * Default using H2-Database.
     */
    protected Module getProviderModule() {
        return new JpaModule_H2();
    }

    protected final <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    @Test
    public void testDb()
            throws SQLException {
        CustomerDAO customerDao = getInstance(CustomerDAO.class);

        Customer customer = new Customer();
        customer.setName("lenik");
        customerDao.saveInNewTransaction(customer);

        System.out.println(customer.getId() + "/" + customer.getName());

        Customer retrieved = customerDao.getCustomer("lenik");

        Assert.assertEquals(customer.getId(), retrieved.getId());
        Assert.assertEquals(customer.getName(), retrieved.getName());
    }

}
