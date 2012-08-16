package com.bee32.sem.salary.api;

import java.util.ServiceLoader;

import com.bee32.sem.api.ISalaryVariableProvider;

public class SalayProviderTest {

    public static void main(String[] args) {
        ServiceLoader<ISalaryVariableProvider> providers = ServiceLoader.load(ISalaryVariableProvider.class);
        for (ISalaryVariableProvider prov : providers)
            System.out.println(prov);
    }
}
