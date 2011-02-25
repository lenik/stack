package com.bee32.plover.arch.service;

import com.bee32.plover.arch.IComponent;

public interface IServiceRepository
        extends IComponent {

    <C extends IServiceContribution<C>> Iterable<C> getContributions(Class<C> contributionClass);

    void contribute(IServiceContribution<?> contribution);

}
