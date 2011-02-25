package com.bee32.plover.arch.service;

import com.bee32.plover.arch.Component;

public class ServiceContribution<C extends IServiceContribution<C>>
        extends Component
        implements IServiceContribution<C> {

    private final Class<C> contributionClass;

    public ServiceContribution(Class<C> contributionClass) {
        super();
        if (contributionClass == null)
            throw new NullPointerException("contributionClass");
        this.contributionClass = contributionClass;
    }

    public ServiceContribution(String name, Class<C> contributionClass) {
        super(name);
        if (contributionClass == null)
            throw new NullPointerException("contributionClass");
        this.contributionClass = contributionClass;
    }

    @Override
    public Class<C> getContributionClass() {
        return contributionClass;
    }

}
