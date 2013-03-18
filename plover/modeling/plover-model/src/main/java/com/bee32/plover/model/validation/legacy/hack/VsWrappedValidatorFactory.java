package com.bee32.plover.model.validation.legacy.hack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.spi.ConfigurationState;

import org.hibernate.validator.engine.ValidatorFactoryImpl;
import org.hibernate.validator.metadata.ConstraintHelper;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.bee32.plover.model.validation.ValidationSwitcherManager;

/**
 * Delegate the builtin validator using cglib.
 */
public class VsWrappedValidatorFactory
        extends ValidatorFactoryImpl {

    public VsWrappedValidatorFactory(ConfigurationState configurationState) {
        super(configurationState);

        ConstraintHelper helper = ValidatorFactoryImplHacker.getConstraintHelper(this);
        Map<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>> map = ConstraintHelperHacker
                .getConstraintValidatorDefinitions(helper);
        System.out.println(map);

        for (List<Class<? extends ConstraintValidator<? extends Annotation, ?>>> list : map.values()) {
            for (int i = 0; i < list.size(); i++) {
                Class<? extends ConstraintValidator<? extends Annotation, ?>> validatorType = list.get(i);
                Class<? extends ConstraintValidator<? extends Annotation, ?>> wrappedType = getVsTypeWrapper(validatorType);
                list.set(i, wrappedType);
            }
        }
    }

    static Map<Class<?>, Class<?>> wrappedTypes = new HashMap<Class<?>, Class<?>>();

    public static <T> Class<T> getVsTypeWrapper(Class<T> orig) {
        Class<T> wrapped = (Class<T>) wrappedTypes.get(orig);
        if (wrapped == null) {
            Enhancer enhancer = new Enhancer();

            // enhancer.setNamingPolicy(namingPolicy);

            enhancer.setSuperclass(orig);
            enhancer.setCallback(VsWrapperIntereceptor.INSTANCE);

            wrapped = enhancer.createClass();
            wrappedTypes.put(orig, wrapped);
        }
        return wrapped;
    }

    static class VsWrapperIntereceptor
            implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                throws Throwable {

            if ("isValid".equals(method.getName())) {
                if (!ValidationSwitcherManager.isValidationEnabled())
                    return true;
            }

            return proxy.invokeSuper(obj, args);
        }

        public static final VsWrapperIntereceptor INSTANCE = new VsWrapperIntereceptor();

    }

}

class ValidatorFactoryImplHacker {

    static final Field constraintHelperField;

    static {
        try {
            constraintHelperField = ValidatorFactoryImpl.class.getDeclaredField("constraintHelper");
        } catch (ReflectiveOperationException e) {
            throw new Error(e.getMessage(), e);
        }
        constraintHelperField.setAccessible(true);
    }

    public static ConstraintHelper getConstraintHelper(ValidatorFactoryImpl impl) {
        try {
            return (ConstraintHelper) constraintHelperField.get(impl);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void setConstraintHelper(ValidatorFactoryImpl impl, ConstraintHelper helper) {
        try {
            constraintHelperField.set(impl, helper);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}

class ConstraintHelperHacker {

    static final Field constraintValidatorDefinitionsField;

    static {
        try {
            constraintValidatorDefinitionsField = ConstraintHelper.class
                    .getDeclaredField("constraintValidatorDefinitions");
        } catch (ReflectiveOperationException e) {
            throw new Error(e.getMessage(), e);
        }
        constraintValidatorDefinitionsField.setAccessible(true);
    }

    public static Map<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>> getConstraintValidatorDefinitions(
            ConstraintHelper helper) {
        Map<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>> map;
        try {
            map //
            = (Map<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>>) //
            constraintValidatorDefinitionsField.get(helper);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return map;
    }

    public static void setConstraintValidatorDefinitions(ConstraintHelper helper,
            Map<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>> map) {
        try {
            constraintValidatorDefinitionsField.set(helper, map);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}