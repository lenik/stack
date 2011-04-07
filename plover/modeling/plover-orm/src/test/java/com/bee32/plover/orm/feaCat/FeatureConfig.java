package com.bee32.plover.orm.feaCat;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.orm.test.bookstore.Book;

@Import(ScanTestxContext.class)
@Lazy
public class FeatureConfig {

    @Inject
    ApplicationContext context;

    @Bean
    FeatureSFB sessionFactory() {
        return new FeatureSFB();
    }

    @Bean
    Book mybook() {
        return new Book("my", "book");
    }

}
