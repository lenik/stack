package com.bee32.plover.inject.spring;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import org.junit.Test;
import org.springframework.context.annotation.ScopeMetadata;

public class TrueJsr330ScopeMetadataResolverTest {

    TrueJsr330ScopeMetadataResolver resolver = new TrueJsr330ScopeMetadataResolver();

    @javax.inject.Singleton
    static class SingletonBean {
    }

    @Scope
    @Inherited
    @Retention(RUNTIME)
    @interface FooScope {
    }

    @FooScope
    static class FooBean {
    }

    static class Foo2Bean
            extends FooBean {
    }

    @Test
    public void testJsr330Singleton() {
        ScopeMetadata metadata = resolver.getScopeMetadata(SingletonBean.class);
        assertEquals("singleton", metadata.getScopeName());
    }

    @Test
    public void testCustomScopeAnnotation() {
        ScopeMetadata metadata = resolver.getScopeMetadata(FooBean.class);
        assertEquals("foo", metadata.getScopeName());
    }

    @Test
    public void testInheritedCustomScopeAnnotation() {
        ScopeMetadata metadata = resolver.getScopeMetadata(Foo2Bean.class);
        assertEquals("foo", metadata.getScopeName());
    }

}
