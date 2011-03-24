package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.context.LocationContextConstants;
import com.bee32.sem.frame.Contribution;

/**
 * Class derives {@link MenuContribution} will be served as singleton instantiated beans.
 * <p>
 * After all beans are initialized, the instances of {@link MenuContribution}s are then be collected
 * by MenuManager.
 */
@ComponentTemplate
public abstract class MenuContribution
        extends Composite
        implements LocationContextConstants {

    private Map<String, IMenuItem> contributions = new LinkedHashMap<String, IMenuItem>();

    public MenuContribution() {
        super();
    }

    public MenuContribution(String name) {
        super(name);
    }

    @Override
    protected void introduce() {
        super.introduce();

        for (Field field : getElementFields()) {

            Contribution contribAnn = field.getAnnotation(Contribution.class);
            if (contribAnn == null)
                continue;

            String targetPath = contribAnn.value();
            assert targetPath != null;

            Class<?> fieldType = field.getType();

            if (!IMenuItem.class.isAssignableFrom(fieldType))
                throw new UnsupportedOperationException("Bad contribution element type: " + field);

            Object fieldValue;
            try {
                fieldValue = field.get(this);
            } catch (Exception e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            contribute(targetPath, (IMenuItem) fieldValue);

        }
    }

    protected void contribute(String parentMenuPath, IMenuItem element) {
        contributions.put(parentMenuPath, element);
    }

    synchronized Map<String, IMenuItem> dump() {

        if (this.contributions == null)
            throw new IllegalStateException("Already dumped");

        getPropertyBinding().bind(getClass());

        Map<String, IMenuItem> retval = this.contributions;
        this.contributions = null;
        return retval;
    }

}
