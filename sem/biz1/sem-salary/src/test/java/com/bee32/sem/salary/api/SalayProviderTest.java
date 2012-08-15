package com.bee32.sem.salary.api;

import java.util.ServiceLoader;

import com.bee32.sem.api.ISalaryProvider;

public class SalayProviderTest {

    public static void main(String[] args) {
        ServiceLoader<ISalaryProvider> providers = ServiceLoader.load(ISalaryProvider.class);
        for (ISalaryProvider prov : providers)
            System.out.println(prov);
    }
}
