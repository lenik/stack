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
import com.bee32.plover.servlet.context.LocationContextConstants;
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
        implements LocationContextConstants {

    private List<Entry<String, IMenuItem>> contributions = new ArrayList<Map.Entry<String, IMenuItem>>();

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
            if (!IMenuItem.class.isAssignableFrom(fieldType))
                throw new UnsupportedOperationException("Bad contribution element type: " + field);

            IMenuItem menuItem;
            try {
                menuItem = (IMenuItem) field.get(this);
            } catch (Exception e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            // String name = menuItem.getName();
            // String targetPath = parentPath + "/" + name;
            contribute(parentPath, (IMenuItem) menuItem);
        }
    }

    protected final void contribute(String parentMenuPath, IMenuItem element) {
        Pair<String, IMenuItem> entry = new Pair<String, IMenuItem>(parentMenuPath, element);
        contributions.add(entry);
    }

    synchronized final List<Entry<String, IMenuItem>> dump() {
        if (this.contributions == null)
            throw new IllegalStateException("Already dumped");

        assemble();

        IProperties properties = new ClassResourceProperties(getClass(), Locale.getDefault());
        setProperties(properties);

        List<Entry<String, IMenuItem>> retval = this.contributions;
        this.contributions = null;
        return retval;
    }

}
