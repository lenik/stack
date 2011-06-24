package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.IllegalUsageException;
import javax.free.Pair;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.arch.util.res.ClassResourceProperties;
import com.bee32.plover.arch.util.res.IProperties;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.ext.dict.DictEntity;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.Contribution;

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

    private List<Entry<String, IMenuEntry>> contributions = new ArrayList<Map.Entry<String, IMenuEntry>>();

    public MenuContribution() {
        super();
    }

    public MenuContribution(String name) {
        super(name);
    }

    @Override
    protected final void introduce() {
        super.introduce();

        for (Field field : getElementFields()) {

            Contribution contribAnn = field.getAnnotation(Contribution.class);
            if (contribAnn == null)
                continue;

            String parentPath = contribAnn.value();
            assert parentPath != null;

            Class<?> fieldType = field.getType();
            if (!IMenuEntry.class.isAssignableFrom(fieldType))
                throw new UnsupportedOperationException("Bad contribution element type: " + field);

            IMenuEntry menuEntry;
            try {
                menuEntry = (IMenuEntry) field.get(this);
            } catch (Exception e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            // String name = menuEntry.getName();
            // String targetPath = parentPath + "/" + name;
            contribute(parentPath, (IMenuEntry) menuEntry);
        }
    }

    protected final void contribute(String parentMenuPath, IMenuEntry element) {
        Pair<String, IMenuEntry> node = new Pair<String, IMenuEntry>(parentMenuPath, element);
        contributions.add(node);
    }

    synchronized final List<Entry<String, IMenuEntry>> dump() {
        if (this.contributions == null)
            throw new IllegalStateException("Already dumped");

        assemble();

        IProperties properties = new ClassResourceProperties(getClass(), Locale.getDefault());
        setProperties(properties);

        List<Entry<String, IMenuEntry>> retval = this.contributions;
        this.contributions = null;
        return retval;
    }

    // Helpers.

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX + "/");

    protected static Location getDictIndex(Class<? extends DictEntity<?>> dictType) {
        Location dictIndex = DICT.join(ABBR.abbr(dictType) + "/index.do");
        return dictIndex;
    }

}
