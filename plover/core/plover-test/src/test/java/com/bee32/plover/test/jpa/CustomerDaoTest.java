package com.bee32.plover.test.jpa;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class CustomerDaoTest
        extends JpaTestCase {

    @Test
    public void testCustomerGetSet()
            throws SQLException {
        CustomerDao customerDao = getInstance(CustomerDao.class);

        Customer customer = new Customer();
        customer.setName("lenik");
        customerDao.saveInNewTransaction(customer);

        System.out.println(customer.getId() + "/" + customer.getName());

        Customer retrieved = customerDao.getCustomer("lenik");

        Assert.assertEquals(customer.getId(), retrieved.getId());
        Assert.assertEquals(customer.getName(), retrieved.getName());
    }

}
