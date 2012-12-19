package com.bee32.sem.frame.menu;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.arch.IAppProfile;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.PloverOx1Module;
import com.bee32.plover.ox1.dict.DictEntity;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.plover.ox1.dict.NameDictDto;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.sem.frame.action.Action;

@ServiceTemplate(prototype = true)
public abstract class MenuComposite
        extends Composite
        implements ILocationConstants, ITypeAbbrAware {

    static Logger logger = LoggerFactory.getLogger(MenuComposite.class);

    public static final String ENABLED = "enabled";

    boolean enabled;
    List<Field> fields;
    Map<String, MenuNode> localMap = new HashMap<String, MenuNode>();

    public MenuComposite() {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        IAppProfile mergedProfile = site.getMergedProfile();
        Boolean enabled = (Boolean) mergedProfile.getParameter(getClass(), MenuComposite.ENABLED);
        this.enabled = enabled == Boolean.TRUE;
    }

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

        // Load resources and attach to the parent of if the composite is enabled.
        if (enabled) {
            if (parent != null)
                if (!parent.add(node))
                    throw new IllegalUsageException("Duplicated menu node: " + node + ", parent: " + parent);
            declare(name, node);
        }

        return node;
    }

    protected <T extends MenuComposite> T require(Class<T> mcClass) {
        IMenuAssembler asm = ContextMenuAssembler.getMenuAssembler();
        return asm.require(mcClass);
    }

    protected <T> T getParameter(String key) {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        IAppProfile mergedProfile = site.getMergedProfile();
        Class<?> mcClass = getClass();
        T value = (T) mergedProfile.getParameter(mcClass, key);
        return value;
    }

    static Set<Object> TRUE_VALUES = new HashSet<>();
    static {
        TRUE_VALUES.add(true);
        TRUE_VALUES.add("1");
        TRUE_VALUES.add("true");
    }

    protected boolean mode(String key) {
        Object val = getParameter(key);
        if (TRUE_VALUES.contains(val))
            return true;
        else
            return false;
    }

    // Helpers.

    static Location DICT = WEB_APP.join(PloverOx1Module.PREFIX + "/dict/");

    protected static Location getDictIndex(Class<? extends DictEntity<?>> dictType) {
        Location dictIndex = DICT.join(ABBR.abbr(dictType) + "/index.do");
        return dictIndex;
    }

    protected static Location simpleDictIndex(String title, Class<? extends NameDict> entityClass,
            Class<? extends NameDictDto<?>> dtoClass) {
        String entity = ABBR.abbr(entityClass);
        String dto = ABBR.abbr(dtoClass);
        try {
            entity = URLEncoder.encode(entity, "UTF-8");
            dto = URLEncoder.encode(dto, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
        Location index = DICT.join("?title=" + title + "&entityClass=" + entity + "&dtoClass=" + dto);
        return index;
    }

}
