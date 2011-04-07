package com.bee32.plover.orm.feaCat;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;

@Import(ScanTestxContext.class)
@Lazy
public class FeatureConfig {

    @Inject
    ApplicationContext context;

//    @Inject
//    @TestPurpose
//    DataSource tds;

    @Bean
    FeatureSFB featureSFB() {
        // return new FeatureSFB();
        return context.getAutowireCapableBeanFactory().createBean(FeatureSFB.class);
    }

}
