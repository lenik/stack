package com.bee32.plover.arch.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.arch.Component;

public class ServiceRepository
        extends Component
        implements IServiceRepository {

    private Map<Class<?>, List<IServiceContribution<?>>> contributionsMap;
    {
        contributionsMap = new HashMap<Class<?>, List<IServiceContribution<?>>>();
    }

    public ServiceRepository() {
        super();
    }

    public ServiceRepository(String name) {
        super(name);
    }

    @Override
    public <C extends IServiceContribution<C>> Iterable<C> getContributions(Class<C> contributionClass) {
        @SuppressWarnings("unchecked")
        List<C> contributionsList = (List<C>) contributionsMap.get(contributionClass);

        if (contributionsList == null)
            return Collections.emptyList();

        return contributionsList;
    }

    public synchronized void contribute(IServiceContribution<?> contribution) {
        Class<?> contributionClass = contribution.getContributionClass();
        List<IServiceContribution<?>> list = contributionsMap.get(contributionClass);
        if (list == null) {
            list = new ArrayList<IServiceContribution<?>>();
            contributionsMap.put(contributionClass, list);
        }
        list.add(contribution);
    }

}
