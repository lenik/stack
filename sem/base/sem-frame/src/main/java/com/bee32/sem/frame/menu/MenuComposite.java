package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.dict.CommonDictController;
import com.bee32.plover.ox1.dict.DictEntity;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.action.Action;

/**
 * Class derives {@link MenuComposite} will be served as singleton instantiated beans.
 * <p>
 * After all beans are initialized, the instances of {@link MenuComposite}s are then be collected by
 * MenuManager.
 *
 * TODO - Locale-local allocation.
 */
@ComponentTemplate
public abstract class MenuComposite
        extends Composite
        implements ILocationConstants, ITypeAbbrAware {

    List<Field> fields;
    Map<String, MenuNode> localMap = new HashMap<String, MenuNode>();

    @Override
    protected boolean isUsingComponentName() {
        return true;
    }

    @Override
    protected final void scan() {
        declare("", this);
        preamble();
    }

    protected void preamble() {
    }

    /**
     * Force load of NLS.
     */
    public synchronized final Map<String, MenuNode> prepareMap() {
        // Invoke the introduce/preambles.
        assembleOnce();
        return localMap;
    }

    protected MenuNode menu(String name) {
        return menu(null, 0, name);
    }

    protected MenuNode menu(int order, String name) {
        return entry(null, order, name, null);
    }

    @Deprecated
    protected MenuNode menu(MenuNode parent, String name) {
        return menu(parent, 0, name);
    }

    protected MenuNode menu(MenuNode parent, int order, String name) {
        return entry(parent, order, name, null);
    }

    @Deprecated
    protected MenuNode section(MenuNode parent, String name) {
        return section(parent, 0, name);
    }

    protected MenuNode section(MenuNode parent, int order, String name) {
        MenuNode node = menu(parent, order, name);
        node.setFlatten(true);
        return node;
    }

    static int separatorId = 0;

    protected MenuNode _separator_(MenuNode parent, int order) {
        MenuNode separator = entry(parent, order, "separator" + (++separatorId), null);
        separator.setFlags(IMenuEntry.SEPARATOR);
        return separator;
    }

    @Deprecated
    protected MenuNode entry(MenuNode parent, String name, ILocationContext location) {
        return entry(parent, 0, name, location);
    }

    protected MenuNode entry(MenuNode parent, int order, String name, ILocationContext location) {
        MenuNode node = new MenuNode(name);

        node.setOrder(order);

        if (location != null) {
            Action action = new Action(location);
            node.setAction(action);
        }

        if (parent != null)
            if (!parent.add(node))
                throw new IllegalUsageException("Duplicated menu node: " + node + ", parent: " + parent);

        declare(name, node);
        return node;
    }

    protected <T extends MenuComposite> T require(Class<T> mcClass) {
        return null;
    }

    // Helpers.

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX + "/");

    protected static Location getDictIndex(Class<? extends DictEntity<?>> dictType) {
        Location dictIndex = DICT.join(ABBR.abbr(dictType) + "/index.do");
        return dictIndex;
    }

}
