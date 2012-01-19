package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.arch.util.res.ClassResourceProperties;
import com.bee32.plover.arch.util.res.IProperties;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.dict.CommonDictController;
import com.bee32.plover.ox1.dict.DictEntity;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.action.Action;

/**
 * Class derives {@link MenuContribution} will be served as singleton instantiated beans.
 * <p>
 * After all beans are initialized, the instances of {@link MenuContribution}s are then be collected
 * by MenuManager.
 *
 * TODO - Locale-local allocation.
 */
@ComponentTemplate
public abstract class MenuContribution
        extends Composite
        implements ILocationConstants, ITypeAbbrAware {

    List<Field> fields;
    Map<String, MenuNode> localMap = new HashMap<String, MenuNode>();

    @Override
    protected final void introduce() {
        super.introduce();

        for (Field field : getElementFields()) {

            MenuNode menuNode;
            try {
                menuNode = (MenuNode) field.get(this);
            } catch (Exception e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            // String name = menuEntry.getName();
            // String targetPath = parentPath + "/" + name;
            localMap.put(field.getName(), menuNode);
        }
    }

    /**
     * Scan all MenuNode(s) instead of those &#64;Contribution-annotated.
     */
    @Override
    protected synchronized List<Field> getElementFields() {
        if (fields != null)
            return fields;

        fields = new ArrayList<Field>();
        Class<?> clazz = getClass();

        while (clazz != MenuContribution.class) {

            Field[] declaredFields = getClass().getDeclaredFields();
            for (Field field : declaredFields) {

                if (!MenuNode.class.isAssignableFrom(field.getType()))
                    continue;

                int modifiers = field.getModifiers();
                if (Modifier.isTransient(modifiers))
                    continue;

                if (!Modifier.isPublic(modifiers))
                    field.setAccessible(true);

                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    // protected final void declare(MenuNode node) {
    // String propId = node.getName();
    // declare(propId, node);
    // localMap.put(propId, node);
    // }

    // XXX Remove public in future?
    public synchronized final Map<String, MenuNode> dump() {
        // Invoke the introduce/preambles.
        assemble();

        // otherwise, the properties are set by menu UI builder.
        IProperties properties = new ClassResourceProperties(getClass(), Locale.getDefault());
        setProperties(properties);

        return localMap;
    }

    protected static MenuNode menu(String name) {
        return menu(null, 0, name);
    }

    protected static MenuNode menu(int order, String name) {
        return entry(null, order, name, null);
    }

    @Deprecated
    protected static MenuNode menu(MenuNode parent, String name) {
        return menu(parent, 0, name);
    }

    protected static MenuNode menu(MenuNode parent, int order, String name) {
        return entry(parent, order, name, null);
    }

    @Deprecated
    protected static MenuNode section(MenuNode parent, String name) {
        return section(parent, 0, name);
    }

    protected static MenuNode section(MenuNode parent, int order, String name) {
        MenuNode node = menu(parent, order, name);
        node.setFlatten(true);
        return node;
    }

    @Deprecated
    protected static MenuNode entry(MenuNode parent, String name, ILocationContext location) {
        return entry(parent, 0, name, location);
    }

    protected static MenuNode entry(MenuNode parent, int order, String name, ILocationContext location) {
        MenuNode node = new MenuNode(name);

        node.setOrder(order);

        if (location != null) {
            Action action = new Action(location);
            node.setAction(action);
        }

        if (parent != null)
            if (!parent.add(node))
                throw new IllegalUsageException("Duplicated menu node: " + node + ", parent: " + parent);

        return node;
    }

    // Helpers.

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX + "/");

    protected static Location getDictIndex(Class<? extends DictEntity<?>> dictType) {
        Location dictIndex = DICT.join(ABBR.abbr(dictType) + "/index.do");
        return dictIndex;
    }

}
