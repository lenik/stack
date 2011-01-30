package com.bee32.plover.test.jpa;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CustomerDAOTest
        extends JpaTestCase {

    @Test
    public void testDb()
            throws SQLException {
        Injector injector = Guice.createInjector(new JpaModule_H2());
        CustomerDAO customerDao = injector.getInstance(CustomerDAO.class);

        Customer customer = new Customer();
        customer.setName("lenik");
        customerDao.saveInNewTransaction(customer);

        System.out.println(customer.getId() + "/" + customer.getName());

        Customer retrieved = customerDao.getCustomer("lenik");

        Assert.assertEquals(customer.getId(), retrieved.getId());
        Assert.assertEquals(customer.getName(), retrieved.getName());
    }

}
