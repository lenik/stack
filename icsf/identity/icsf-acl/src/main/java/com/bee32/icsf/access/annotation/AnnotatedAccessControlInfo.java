package com.bee32.icsf.access.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import com.bee32.icsf.access.IAccessControlInfo;
import com.bee32.icsf.access.IManagedOperation;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.SimpleManagedOperation;

public class AnnotatedAccessControlInfo
        implements IAccessControlInfo {

    private Class<?> clazz;
    private boolean loaded;

    // private List<IManagedOperation> tiny;
    private Map<String, IManagedOperation> mass;

    public AnnotatedAccessControlInfo(Class<? /* extends IManagedOperation */> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
    }

    @Override
    public Collection<? extends IManagedOperation> getManagedOperations() {
        loadAnnotations();
        return mass.values();
    }

    @Override
    public final Permission getRequiredPermission(String operationName) {
        if (operationName == null)
            throw new NullPointerException("operationName");
        loadAnnotations();
        IManagedOperation o = mass.get(operationName);
        if (o == null)
            return null;
        return o.getRequiredPermission();
    }

    private synchronized void loadAnnotations() {
        if (!loaded) {
            Locale locale = Locale.getDefault();
            final ResourceBundle resourceBundle = _getBundle(clazz, locale);

            class MOParser {
                IManagedOperation parse(ManagedOperation mo, String defaultName) {
                    String name = mo.name();
                    if (name == null)
                        name = defaultName;
                    String displayName = _getString(resourceBundle, name + "_displayName");
                    String description = _getString(resourceBundle, name + "_description");

                    int visibility = mo.visibility();
                    if (visibility == ManagedOperation.REFLECTED)
                        switch (clazz.getModifiers() & (Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE)) {
                        case Modifier.PUBLIC:
                            visibility = IManagedOperation.PUBLIC;
                            break;
                        case Modifier.PROTECTED:
                            visibility = IManagedOperation.MODULE;
                            break;
                        default:
                            visibility = IManagedOperation.PRIVATE;
                        }

                    Permission permission = getOverridedRequiredPermission(name);
                    if (permission == null) {
                        Class<? extends Permission> permissionClass = mo.require();
                        if (DefaultPermission.class.equals(permissionClass)) {
                            try {
                                Field permissionField = permissionClass.getDeclaredField(name + "Permission");
                                permission = (Permission) permissionField.get(null);
                            } catch (NoSuchFieldException e) {
                                // no permission field, skip.
                            } catch (Exception e) {
                                throw new BadAnnotationException(e.getMessage(), e);
                            }
                        } else {
                            try {
                                permission = (Permission) permissionClass.newInstance();
                            } catch (Exception e) {
                                throw new BadAnnotationException(e.getMessage(), e);
                            }
                        }
                    }

                    return new SimpleManagedOperation(visibility, name, displayName, description, permission);
                }
            }

            MOParser moParser = new MOParser();

            ManagedOperation mo = clazz.getAnnotation(ManagedOperation.class);
            if (mo != null)
                addManagedOperation(moParser.parse(mo, clazz.getSimpleName()));

            for (Method method : clazz.getDeclaredMethods()) {
                mo = method.getAnnotation(ManagedOperation.class);
                if (mo != null)
                    addManagedOperation(moParser.parse(mo, method.getName()));
            }

            loaded = true;
        }
    }

    protected void addManagedOperation(IManagedOperation operation) {
        if (mass == null)
            mass = new TreeMap<String, IManagedOperation>();
        String name = operation.getName();
        mass.put(name, operation);
    }

    private static ResourceBundle _getBundle(Class<?> clazz, Locale locale) {
        String baseName = clazz.getName();
        try {
            return ResourceBundle.getBundle(baseName, locale, clazz.getClassLoader());
        } catch (MissingResourceException e) {
            return null;
        }
    }

    private static String _getString(ResourceBundle bundle, String key) {
        if (bundle == null)
            return null;
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 覆盖注解中的同名操作的权限。
     */
    protected Permission getOverridedRequiredPermission(String operationName) {
        return null;
    }

}
