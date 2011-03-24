package com.bee32.plover.arch;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.res.IPropertyBinding;
import com.bee32.plover.arch.util.res.UniquePrefixStrategy;

public abstract class Composite
        extends Component
        implements IComposite {

    static Logger logger = LoggerFactory.getLogger(Composite.class);

    private UniquePrefixStrategy prefixStrategy = new UniquePrefixStrategy();

    private boolean assembled;

    public Composite() {
        super();
    }

    public Composite(String name) {
        super(name);
    }

    void assemble() {
        if (!assembled) {
            synchronized (this) {
                if (!assembled) {
                    introduce();
                    preamble();
                    assembled = true;
                }
            }
        }
    }

    public IPropertyBinding getPropertyBinding() {
        assemble();
        return prefixStrategy;
    }

    protected Iterable<Field> getElementFields() {
        return ReflectiveChildrenComponents.getImplicitAnalyzer(getClass());
    }

    protected void introduce() {
        declare("", this);

        boolean usingComponentName = isUsingComponentName();

        for (Field field : getElementFields()) {
            String name = field.getName();
            Component childComponent;

            try {
                childComponent = (Component) field.get(this);
            } catch (ReflectiveOperationException e) {
                throw new IllegalUsageException("", e);
            }

            if (childComponent == null)
                throw new IllegalUsageException("Null element: " + field);

            if (usingComponentName) {
                String componentName = childComponent.getName();
                if (componentName != null)
                    name = componentName;
            }

            declare(name, childComponent);
        }

    }

    protected boolean isUsingComponentName() {
        return true;
    }

    protected boolean isFallbackEnabled() {
        return false;
    }

    protected void declare(String id, Component childComponent) {
        // logger.debug("declare " + id + " in " + this);

        IAppearance fallback = null;
        if (isFallbackEnabled())
            fallback = childComponent.getAppearance();

        Class<?> compositeClass = getClass();
        InjectedAppearance childAppearanceOverride = new InjectedAppearance(this, fallback, compositeClass);

        childComponent.setAppearance(childAppearanceOverride);

        String prefix = id.isEmpty() ? "" : (id + ".");
        prefixStrategy.registerPrefix(prefix, childAppearanceOverride);
    }

    protected abstract void preamble();

}

class ReflectiveChildrenComponents
        extends ArrayList<Field> {

    private static final long serialVersionUID = 1L;

    public ReflectiveChildrenComponents(Class<?> declaringClass) {
        if (declaringClass == null)
            throw new NullPointerException("declaringClass");

        while (declaringClass != null) {
            for (Field field : declaringClass.getDeclaredFields())
                if (isCompositeChild(field)) {
                    field.setAccessible(true);
                    this.add(field);
                }

            declaringClass = declaringClass.getSuperclass();
        }
    }

    protected boolean isCompositeChild(Field field) {
        int modifiers = field.getModifiers();

        if (Modifier.isStatic(modifiers))
            return false;

        if (!CompositeElementUtil.isCompositeElement(field))
            return false;

        Class<?> fieldType = field.getType();
        if (!Component.class.isAssignableFrom(fieldType))
            throw new IllegalUsageException("CompositeChild must be " + Component.class);

        return true;
    }

    static ClassLocal<ReflectiveChildrenComponents> classReflectiveChildren = new ClassLocal<ReflectiveChildrenComponents>();

    static ReflectiveChildrenComponents getImplicitAnalyzer(Class<?> clazz) {
        ReflectiveChildrenComponents reflectiveChildren = classReflectiveChildren.get(clazz);
        if (reflectiveChildren == null) {
            synchronized (ReflectiveChildrenComponents.class) {
                reflectiveChildren = classReflectiveChildren.get(clazz);
                if (reflectiveChildren == null) {
                    reflectiveChildren = new ReflectiveChildrenComponents(clazz);
                    classReflectiveChildren.put(clazz, reflectiveChildren);
                }
            }
        }
        return reflectiveChildren;
    }

}
