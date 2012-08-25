package com.bee32.sem.salary.api;

import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;

public class SalayProviderTest {

    public static void main(String[] args) {
        for (ISalaryVariableProvider prov : SalaryVariableProviders.getProviders())
            System.out.println(prov);
    }

}
