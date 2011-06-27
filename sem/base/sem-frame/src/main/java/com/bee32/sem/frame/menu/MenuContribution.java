package com.bee32.sem.frame.menu;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.ext.dict.DictEntity;
import com.bee32.plover.orm.util.ITypeAbbrAware;
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

    protected static MenuNode menu(String name) {
        return menu(null, 0, name);
    }

    protected static MenuNode menu(int order, String name) {
        return entry(null, order, name, null);
    }

    protected static MenuNode menu(MenuNode parent, String name) {
        return menu(parent, 0, name);
    }

    protected static MenuNode menu(MenuNode parent, int order, String name) {
        return entry(parent, order, name, null);
    }

    protected static MenuNode section(MenuNode parent, String name) {
        return section(parent, 0, name);
    }

    protected static MenuNode section(MenuNode parent, int order, String name) {
        MenuNode node = menu(parent, order, name);
        node.setFlatten(true);
        return node;
    }

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
