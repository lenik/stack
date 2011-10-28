package com.bee32.plover.inject.spring;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.Strings;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopedProxyMode;

import com.bee32.plover.arch.util.ClassUtil;

public class TrueJsr330ScopeMetadataResolver
        extends AnnotationScopeMetadataResolver {

    public static String DEFAULT_SCOPE_NAME = "singleton";
    static String springScopeAnn = Scope.class.getName();

    private final ScopedProxyMode defaultProxyMode;

    public TrueJsr330ScopeMetadataResolver() {
        super();
        this.defaultProxyMode = ScopedProxyMode.NO;
    }

    public TrueJsr330ScopeMetadataResolver(ScopedProxyMode defaultProxyMode) {
        super(defaultProxyMode);
        this.defaultProxyMode = defaultProxyMode;
    }

    @Override
    public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
        if (!(definition instanceof AnnotatedBeanDefinition))
            return super.resolveScopeMetadata(definition);

        AnnotatedBeanDefinition annDef = (AnnotatedBeanDefinition) definition;

        String scopeName = null;
        ScopedProxyMode proxyMode = null;

        // OPT - Check if duplicated scope annotation.
        for (String annotationType : annDef.getMetadata().getAnnotationTypes()) {
            if (springScopeAnn.equals(annotationType)) {
                Map<String, Object> attributes = annDef.getMetadata().getAnnotationAttributes(annotationType);
                scopeName = (String) attributes.get("value");
                proxyMode = (ScopedProxyMode) attributes.get("proxyMode");
                break;
            }
            Class<? extends Annotation> annotationClass = resolveScopeAnnotation(annotationType);
            if (annotationClass != null) {
                scopeName = getScopeName(annotationClass);
                break;
            }
        }

        if (scopeName == null) {
            // Try resolve scope by JRE.
            String beanClassName = annDef.getBeanClassName();
            Class<?> beanClass;
            try {
                beanClass = ClassUtil.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            ScopeMetadata metadata = getScopeMetadata(beanClass);
            if (metadata != null)
                return metadata;
        }

        if (scopeName == null)
            scopeName = DEFAULT_SCOPE_NAME;

        if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT)
            proxyMode = this.defaultProxyMode;

        ScopeMetadata metadata = new ScopeMetadata();
        metadata.setScopeName(scopeName);
        metadata.setScopedProxyMode(proxyMode);
        return metadata;
    }

    public ScopeMetadata getScopeMetadata(Class<?> beanClass) {
        for (Annotation annotation : beanClass.getAnnotations()) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();

            if (isScopeAnnotation(annotationClass)) {
                String scopeName = getScopeName(annotationClass);

                if (scopeName != null) {
                    ScopeMetadata metadata = new ScopeMetadata();
                    metadata.setScopeName(scopeName);

                    ScopedProxyMode proxyMode = defaultProxyMode;
                    ScopeProxy scopeProxy = beanClass.getAnnotation(ScopeProxy.class);
                    if (scopeProxy != null)
                        proxyMode = scopeProxy.value();

                    metadata.setScopedProxyMode(proxyMode);

                    return metadata;
                }
            } // isScopeAnnotation?
        } // for annotation
        return null;
    }

    public static Class<? extends Annotation> resolveScopeAnnotation(String annotationClassName) {
        try {
            Class<?> clazz = Class.forName(annotationClassName);
            if (!clazz.isAnnotation())
                return null;

            @SuppressWarnings("unchecked")
            Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) clazz;

            if (!isScopeAnnotation(annotationClass))
                return null;

            return annotationClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isScopeAnnotation(Class<? extends Annotation> annotationClass) {
        javax.inject.Scope jsr330Scope = annotationClass.getAnnotation(javax.inject.Scope.class);
        return jsr330Scope != null;
    }

    private static Map<Class<? extends Annotation>, String> scopeNameMap = new HashMap<Class<? extends Annotation>, String>();

    public static String getScopeName(Class<? extends Annotation> scopeAnnotationClass) {
        if (scopeAnnotationClass == null)
            throw new NullPointerException("scopeAnnotationClass");

        String scopeName = scopeNameMap.get(scopeAnnotationClass);
        if (scopeName == null) {
            scopeName = _getScopeName(scopeAnnotationClass);
            scopeNameMap.put(scopeAnnotationClass, scopeName);
        }

        return scopeName;
    }

    /**
     * @return Non-empty scope name.
     */
    static String _getScopeName(Class<? extends Annotation> scopeAnnotationClass) {
        Scope _springScope = scopeAnnotationClass.getAnnotation(org.springframework.context.annotation.Scope.class);
        if (_springScope != null) {
            String springScopeName = _springScope.value();
            if (springScopeName != null && !springScopeName.isEmpty())
                return springScopeName;
        }

        // Test: scopeAnnotationClass < javax.inject.Scope
        ScopeName _scopeName = scopeAnnotationClass.getAnnotation(ScopeName.class);
        if (_scopeName != null) {
            String scopeName = _scopeName.value();
            return scopeName;
        }

        String simpleName = scopeAnnotationClass.getSimpleName();
        String defaultScopeName = Strings.lcfirst(simpleName);
        return defaultScopeName;
    }

}
