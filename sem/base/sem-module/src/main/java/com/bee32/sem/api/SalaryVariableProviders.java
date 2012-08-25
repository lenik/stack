package com.bee32.sem.api;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SalaryVariableProviders {

    public static List<ISalaryVariableProvider> getProviders() {
        List<ISalaryVariableProvider> providers = new ArrayList<ISalaryVariableProvider>();
        for (ISalaryVariableProvider provider : ServiceLoader.load(ISalaryVariableProvider.class))
            providers.add(provider);
        return providers;
    }

}
