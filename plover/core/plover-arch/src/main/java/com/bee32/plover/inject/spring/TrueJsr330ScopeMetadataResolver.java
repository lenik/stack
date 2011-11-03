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
import org.springframework.core.type.AnnotationMetadata;

import com.bee32.plover.arch.util.ClassUtil;

public class TrueJsr330ScopeMetadataResolver
        extends AnnotationScopeMetadataResolver {

    public static String DEFAULT_SCOPE_NAME = null; // "singleton";
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
        AnnotationMetadata metadata = annDef.getMetadata();

        String scopeName = null;
        ScopedProxyMode proxyMode_ann = null;
        ScopedProxyMode proxyMode_bean = null;

        String beanClassName = annDef.getBeanClassName();
        // Set breakpoint here for specific bean classes.
        if (beanClassName.contains("LoginBean")) {
            ScopeMetadata _sup = super.resolveScopeMetadata(definition);
            System.out.println(_sup);
        }

        // OPT - Check if duplicated scope annotation.
        for (String annotationType : metadata.getAnnotationTypes()) {
            if (springScopeAnn.equals(annotationType)) {
                Map<String, Object> attributes = metadata.getAnnotationAttributes(annotationType);
                scopeName = (String) attributes.get("value");
                proxyMode_ann = (ScopedProxyMode) attributes.get("proxyMode");
                // Spring @Scope 可以决定一切。
                break;
            }

            // JSR-330 Scope 选项
            Class<? extends Annotation> scopeAnnotationClass = resolveScopeAnnotation(annotationType);
            if (scopeAnnotationClass != null) {
                scopeName = getScopeName(scopeAnnotationClass);
                proxyMode_ann = getScopeProxyMode(scopeAnnotationClass);
                continue;
            }

            // 类上的 ScopeProxy 重定义。
            if (ScopeProxy.class.getName().equals(annotationType)) {
                proxyMode_bean = (ScopedProxyMode) metadata.getAnnotationAttributes(annotationType).get("value");
                continue;
            }
        }

        if (scopeName == null) {
            // Try resolve scope by JRE.
            Class<?> beanClass;
            try {
                beanClass = ClassUtil.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            ScopeMetadata scopeMetadata = getScopeMetadata(beanClass);
            if (scopeMetadata != null)
                return scopeMetadata;
        }

        if (scopeName == null)
            scopeName = DEFAULT_SCOPE_NAME;

        ScopedProxyMode proxyMode = proxyMode_bean;
        if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT) {
            proxyMode = proxyMode_ann;
            if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT)
                proxyMode = defaultProxyMode;
        }

        ScopeMetadata scopeMetadata = new ScopeMetadata();
        if (scopeName != null) {
            scopeMetadata.setScopeName(scopeName);
            scopeMetadata.setScopedProxyMode(proxyMode);
        }
        return scopeMetadata;
    }

    public ScopeMetadata getScopeMetadata(Class<?> beanClass) {
        for (Annotation annotation : beanClass.getAnnotations()) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();

            if (isScopeAnnotation(annotationClass)) {
                String scopeName = getScopeName(annotationClass);

                if (scopeName != null) {
                    ScopeMetadata metadata = new ScopeMetadata();
                    metadata.setScopeName(scopeName);

                    ScopedProxyMode proxyMode = getScopeProxyMode(annotationClass);

                    ScopeProxy _beanScopeProxy = beanClass.getAnnotation(ScopeProxy.class);
                    if (_beanScopeProxy != null)
                        proxyMode = _beanScopeProxy.value();

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
    private static Map<Class<? extends Annotation>, ScopedProxyMode> scopeProxyModeMap = new HashMap<Class<? extends Annotation>, ScopedProxyMode>();

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

    public static ScopedProxyMode getScopeProxyMode(Class<? extends Annotation> scopeAnnotationClass) {
        if (scopeAnnotationClass == null)
            throw new NullPointerException("scopeAnnotationClass");

        ScopedProxyMode proxyMode = scopeProxyModeMap.get(scopeAnnotationClass);
        if (proxyMode == null) {
            ScopeProxy _scopeProxy = scopeAnnotationClass.getAnnotation(ScopeProxy.class);
            if (_scopeProxy != null)
                proxyMode = _scopeProxy.value();
            else
                proxyMode = ScopedProxyMode.DEFAULT;
            scopeProxyModeMap.put(scopeAnnotationClass, proxyMode);
        }

        return proxyMode;
    }

}
