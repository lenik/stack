package com.bee32.plover.test.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;

public class CustomerDao {

    protected EntityManager entityManager;

    @Inject
    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveInNewTransaction(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
    }

    public Customer getCustomer(int id) {
        Query query = entityManager.createQuery("select c from Customer c where c.id = :id");
        query.setParameter("id", id);
        Object result = query.getSingleResult();
        return (Customer) result;
    }

    public Customer getCustomer(String name) {
        Query query = entityManager.createQuery("select c from Customer c where c.name = :name");
        query.setParameter("name", name);
        Object result = query.getSingleResult();
        return (Customer) result;
    }

}
