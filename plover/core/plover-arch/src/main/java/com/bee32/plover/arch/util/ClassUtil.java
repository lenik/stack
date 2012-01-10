package com.bee32.plover.arch.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.free.StringPart;
import javax.free.UnexpectedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.generic.IParameterized;
import com.bee32.plover.arch.generic.IParameterizedType;
import com.bee32.plover.arch.util.res.ResourceBundleUTF8;

public class ClassUtil {

    static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    static boolean useContextClassLoader = false;

    static Class<?> cglibFactoryClass;
    static Class<?> javaassistProxyObjectClass;

    static {
        try {
            cglibFactoryClass = Class.forName("net.sf.cglib.proxy.Factory");
        } catch (ClassNotFoundException e) {
            cglibFactoryClass = null;
        }
        try {
            javaassistProxyObjectClass = Class.forName("javassist.util.proxy.ProxyObject");
        } catch (ClassNotFoundException e) {
            javaassistProxyObjectClass = null;
        }
    }

    public static Class<?> forName(String className)
            throws ClassNotFoundException {

        ClassLoader classLoader = ClassUtil.class.getClassLoader();

        if (useContextClassLoader) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null)
                classLoader = contextClassLoader;
        }

        return Class.forName(className, false, classLoader);
    }

    public static Class<?> skipProxies(Class<?> clazz) {
        if (clazz == null)
            return null;

        // if (baseName.contains("$$"))
        // return getContextURL(clazz.getSuperclass());
        if (cglibFactoryClass != null)
            if (cglibFactoryClass.isAssignableFrom(clazz))
                return skipProxies(clazz.getSuperclass());

        if (javaassistProxyObjectClass != null)
            if (javaassistProxyObjectClass.isAssignableFrom(clazz))
                return skipProxies(clazz.getSuperclass());

        return clazz;
    }

    /**
     * Get the type display name.
     *
     * The display name of a type is defined first in the (package.)Types.properties file, and then
     * (type).properties file is also read if it's not defined in the package NLS.
     *
     * @return The display name.
     */
    public static String getTypeName(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        clazz = ClassUtil.skipProxies(clazz);

        // XXX ResourceBundleNLS not work.
        // ClassNLS nls = ClassNLS.getNLS(clazz);
        // return nls.getLabel();

        String base = clazz.getName().replace('.', '/');
        String displayName = null;
        try {
            ResourceBundle rb = ResourceBundleUTF8.getBundle(base);
            displayName = rb.getString("label");
        } catch (MissingResourceException e) {
            logger.error("Failed to get display name of " + clazz, e);
        }
        if (displayName == null || displayName.isEmpty())
            displayName = clazz.getSimpleName();
        return displayName;
    }

    public static String getParameterizedTypeName(Object instance) {
        if (instance == null)
            throw new NullPointerException("instance");
        if (instance instanceof IParameterized) {
            IParameterizedType type = ((IParameterized) instance).getParameterizedType();
            return type.getDisplayTypeName(instance);
        } else
            return getTypeName(instance.getClass());
    }

    public static URL getContextURL(Class<?> clazz) {
        clazz = skipProxies(clazz);

        String className = clazz.getName();
        String baseName = StringPart.afterLast(className, '.');
        if (baseName == null)
            baseName = className;

        String fileName = baseName + ".class";
        URL resource = clazz.getResource(fileName);
        if (resource == null)
            throw new UnexpectedException("The .class file doesn't exist or can't be resolved: " + clazz);
        return resource;
    }

    public static Set<Class<?>> getClasses(Iterable<? extends Object> objects) {
        Set<Class<?>> set = new HashSet<Class<?>>();

        for (Object obj : objects)
            set.add(obj.getClass());

        return set;
    }

    public static Type[] getImmediatePV(Class<?> clazz) {
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType psuper = (ParameterizedType) superclass;
            return psuper.getActualTypeArguments();
        } else
            return null;
    }

    public static Type[] getOriginPV(Type type) {
        if (type == null)
            throw new NullPointerException("type");

        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            return ptype.getActualTypeArguments();
        }

        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;
            Type superclass = clazz.getGenericSuperclass();
            if (superclass == null)
                return null;
            return getOriginPV(superclass);
        }

        throw new IllegalUsageException("Not a generic type");
    }

    public static Class<?>[] getOriginPVClass(Type type) {
        Type[] pv = getOriginPV(type);
        if (pv == null)
            return null;

        Class<?>[] pvClasses = new Class<?>[pv.length];
        for (int i = 0; i < pv.length; i++)
            pvClasses[i] = bound1(pv[i]);

        return pvClasses;
    }

    /**
     * Recursive get the first concrete type bound of the type (or type variable).
     *
     * @return Non-<code>null</code> first concrete type bound.
     * @throws Error
     *             If failed to get the type bound.
     */
    public static <T> Class<T> bound1(Type type) {
        while (type instanceof TypeVariable<?>) {
            Type[] bounds = ((TypeVariable<?>) type).getBounds();

            // super or extends?
            Type bound1 = bounds[0];

            type = bound1;
        }

        if (type instanceof ParameterizedType)
            type = ((ParameterizedType) type).getRawType();

        if (!(type instanceof Class<?>))
            throw new Error("Failed to get type bound: " + type);

        @SuppressWarnings("unchecked")
        Class<T> cast = (Class<T>) type;

        return cast;
    }

    public static String traceBound(Type type) {
        if (type instanceof TypeVariable<?>) {
            TypeVariable<?> tvar = (TypeVariable<?>) type;

            StringBuilder sb = new StringBuilder();
            sb.append(tvar + "(");

            Type[] bounds = tvar.getBounds();
            for (int i = 0; i < bounds.length; i++) {
                if (i != 0)
                    sb.append(", ");
                sb.append(traceBound(bounds[i]));
            }

            sb.append(")");
            return sb.toString();
        } else
            return type.toString();
    }

    public static String[] traceBounds(Type[] types) {
        String[] bounds = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            bounds[i] = traceBound(types[i]);
        }
        return bounds;
    }

    public static <T> Class<T> infer1(Class<?> clazz, Class<?> interesting, int index) {
        Type[] typeArgs = getTypeArgs(clazz, interesting);
        return bound1(typeArgs[index]);
    }

    /**
     * This is the same as {@link #mapTypeArgsRec(Class, Class, Type[])} with <code>argv</code> set
     * to <code>clazz.getTypeParameters()</code>.
     */
    public static Type[] getTypeArgs(Class<?> clazz, Class<?> interesting) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (interesting == null)
            throw new NullPointerException("interesting");
        if (!interesting.isAssignableFrom(clazz))
            throw new IllegalUsageException(clazz + " is-not-a " + interesting);

        clazz = skipProxies(clazz); // OPT: why should proxy be skipped?

        TypeVariable<?>[] argv = clazz.getTypeParameters();

        return mapTypeArgsRec(clazz, interesting, argv);
    }

    static Type[] EMPTY = {};

    /**
     * Traverse and search interested base class (<code>interesting</code>) with-in the start class
     * (<code>clazz</code>), and map the type parameters (<code>argv</code>) to be the parameters
     * for the interesting base.
     *
     * <p>
     * If interesting base is not generic, an empty array is immediately returned.
     *
     * <p>
     * If the interesting isn't an interface, no generic interface is looked into. This maybe
     * slightly more efficient then query interesting interface.
     *
     * @param clazz
     *            Start class to traverse.
     * @param interesting
     *            The interesting class or interface. An empty array is immediately returned if
     *            interesting base isn't generic.
     * @param argv
     *            The type parameters which would be suitable for the interesting base.
     * @return Non-<code>null</code> actual parameters for the interesting base.
     * @throws IllegalUsageException
     *             If interesting base doesn't found with-in the start class.
     */
    static Type[] mapTypeArgsRec(Class<?> clazz, Class<?> interesting, Type[] argv) {
        if (clazz == interesting)
            return argv;

        if (interesting.getTypeParameters().length == 0)
            return EMPTY;

        TypeVariable<?>[] declVars = clazz.getTypeParameters();

        if (interesting.isInterface())
            for (Type iface : clazz.getGenericInterfaces()) {
                Class<?> ifaceRaw;
                ParameterizedType iface__;

                if (iface instanceof ParameterizedType) {
                    iface__ = (ParameterizedType) iface;
                    ifaceRaw = (Class<?>) iface__.getRawType();
                } else {
                    iface__ = null;
                    ifaceRaw = (Class<?>) iface;
                }

                if (!interesting.isAssignableFrom(ifaceRaw))
                    continue;

                Type[] actualv = EMPTY;
                if (iface__ != null)
                    actualv = iface__.getActualTypeArguments();

                Type[] mapped = mapTypeArgs(declVars, actualv, argv);

                return mapTypeArgsRec(ifaceRaw, interesting, mapped);
            }

        Class<?> superclassRaw = clazz.getSuperclass();
        if (superclassRaw == null)
            throw new IllegalUsageException("No " + interesting + " matched in base classes");

        Type superclass = clazz.getGenericSuperclass();
        Type[] superArgs;

        if (superclass instanceof ParameterizedType) {
            ParameterizedType superclass__ = (ParameterizedType) superclass;
            Type[] actualv = superclass__.getActualTypeArguments();

            superArgs = mapTypeArgs(declVars, actualv, argv);
        } else {
            superArgs = EMPTY;
        }

        return mapTypeArgsRec(clazz.getSuperclass(), interesting, superArgs);
    }

    static Type[] mapTypeArgs(TypeVariable<?>[] declVars, Type[] actualv, Type[] argv) {
        Type[] mapped = new Type[actualv.length];
        V: for (int i = 0; i < actualv.length; i++) {
            Type actual = actualv[i];
            if (actual instanceof TypeVariable<?>) {

                // declare-var[j] => actual-var[i], then args[j]
                TypeVariable<?> actualVar = (TypeVariable<?>) actual;
                String actualName = actualVar.getName();
                for (int j = 0; j < declVars.length; j++) {
                    if (declVars[j].getName().equals(actualName)) {
                        try {
                            mapped[i] = argv[j];
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(1);
                        }
                        continue V;
                    }
                }
                throw new UnexpectedException("No type parameter with name " + actualName + " is declared.");
            }
            mapped[i] = actual;
        }
        return mapped;
    }

}
