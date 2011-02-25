package com.bee32.plover.arch.service;

/**
 * The default service repository facade.
 */
public class Service {

    private static IServiceRepository repository;
    static {
        repository = new ServiceRepository();
    }

    public static <C extends IServiceContribution<C>> Iterable<C> getContributions(Class<C> contributionClass) {
        return repository.getContributions(contributionClass);
    }

    public static void contribute(IServiceContribution<?> contribution) {
        repository.contribute(contribution);
    }

}
