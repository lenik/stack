package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.Composite;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.builtins.MainMenu;

/**
 * Class derives {@link MenuContribution} will be served as singleton instantiated beans.
 * <p>
 * After all beans are initialized, the instances of {@link MenuContribution}s are then be collected
 * by MenuManager.
 */
@ComponentTemplate
@Lazy
public abstract class MenuContribution
        extends Composite {

    @Inject
    MainMenu mainMenu;

    @Override
    protected void declare(String id, Component childComponent) {
        super.declare(id, childComponent);
    }

    protected void contribute(String parentMenuPath, IMenuItem menuItem) {
        mainMenu.resolveMerge(parentMenuPath, menuItem);
    }

    protected void contribute(String parentMenuPath, IMenuEntry menuEntry) {
        mainMenu.resolveMerge(parentMenuPath, menuEntry);
    }

    private static final int CONTRIB_MENU_ENTRY = 1;
    private static final int CONTRIB_MENU_ITEM = 2;

    void contributeTo(MenuEntry rootEntry)
            throws ReflectiveOperationException {

        for (Field field : getElementFields()) {

            Contribution contribAnn = field.getAnnotation(Contribution.class);
            if (contribAnn == null)
                continue;

            String targetPath = contribAnn.value();
            assert targetPath != null;

            Class<?> fieldType = field.getType();

            int contribType;
            if (IMenuEntry.class.isAssignableFrom(fieldType))
                contribType = CONTRIB_MENU_ENTRY;
            else if (IMenuItem.class.isAssignableFrom(fieldType))
                contribType = CONTRIB_MENU_ITEM;
            else
                throw new UnsupportedOperationException("Bad contribution element type: " + field);

            Object fieldValue = field.get(this);
            switch (contribType) {
            case CONTRIB_MENU_ITEM:
                contribute(targetPath, (IMenuItem) fieldValue);
                break;
            case CONTRIB_MENU_ENTRY:
                contribute(targetPath, (IMenuEntry) fieldValue);
                break;
            }

        }
    }

}
