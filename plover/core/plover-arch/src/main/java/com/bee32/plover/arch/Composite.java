package com.bee32.plover.arch;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.ClassPropertyDispatcher;
import com.bee32.plover.arch.util.res.IPropertyDispatcher;

/**
 * Appearance initialization:
 *
 * <pre>
 * assemble:
 *     field: getElementField
 *         node = (Node) field.
 *         if usingComponentName:
 *             name = node.name
 *         else
 *             name = field.name
 *         declare(name, node).
 * declare(name, node):
 *     node.appearance = lazy-injected:
 *         inject <== propertyDispatcher.prefix-acceptor(name.*)
 * </pre>
 */
public abstract class Composite
        extends Assembled
        implements IComposite {

    static Logger logger = LoggerFactory.getLogger(Composite.class);

    // private Class<?> baseClass;
    private URL contextURL;
    IPropertyDispatcher propertyDispatcher = new ClassPropertyDispatcher(getClass());

    public Composite() {
        super();
        setBaseClass(getClass());
    }

    public Composite(String name) {
        super(name);
        setBaseClass(getClass());
    }

    /**
     * Construct the composite with a default name and based on the given base class.
     * <p>
     * The elements in this Composite will find resources related to the specified base class.
     *
     * @param baseClass
     *            The new base class. Specify to <code>null</code> to use this class (a.k.a.
     *            {@link Object#getClass()}.
     */
    public Composite(Class<?> baseClass) {
        super();
        setBaseClass(baseClass);
    }

    /**
     * Construct a named composite and based on the given base class.
     * <p>
     * The elements in this Composite will find resources related to the specified base class.
     *
     * @param baseClass
     *            The new base class. Specify to <code>null</code> to use this class (a.k.a.
     *            {@link Object#getClass()}.
     */
    public Composite(String name, Class<?> baseClass) {
        super(name);
        setBaseClass(baseClass);
    }

    /**
     * Change the base class.
     * <p>
     * The elements in this Composite will find resources related to the specified base class.
     *
     * @param baseClass
     *            The new base class. Specify to <code>null</code> to use this class (a.k.a.
     *            {@link Object#getClass()}.
     */
    void setBaseClass(Class<?> baseClass) {
        if (baseClass == null)
            baseClass = this.getClass();
        // this.baseClass = baseClass;

        this.contextURL = ClassUtil.getContextURL(baseClass);
    }

    protected Iterable<Field> getElementFields() {
        return ReflectiveChildrenComponents.getImplicitAnalyzer(getClass());
    }

    @Override
    protected void scan() {
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

    /**
     * Get NLS resource by component name instead of field name.
     */
    protected boolean isUsingComponentName() {
        return false;
    }

    protected boolean isFallbackEnabled() {
        return false;
    }

    protected void declare(String propId, Component component) {
        // logger.debug("declare " + id + " in " + this);

        IAppearance fallback = null;
        if (isFallbackEnabled())
            fallback = component.getAppearance();

        InjectedAppearance injectedAppearance = new InjectedAppearance(fallback, contextURL);
        {
            String prefix = propId;
            if (!prefix.isEmpty())
                prefix += ".";

            propertyDispatcher.addPrefixAcceptor(prefix, injectedAppearance);
            injectedAppearance.setPropertyDispatcher(propertyDispatcher);
        }

        ComponentBuilder.setAppearance(component, injectedAppearance);
    }

}

/**
 * <ol>
 * <li>Exclude: static fields.
 * <li>Include: fields annotated with @CompositeElement
 * <li>Include: other field of Component class.
 * </ol>
 */
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
