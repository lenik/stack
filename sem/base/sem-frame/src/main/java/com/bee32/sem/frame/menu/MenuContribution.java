package com.bee32.sem.frame.menu;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.Composite;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.sem.frame.Contribution;

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

    private static final int CONTRIB_N_A = 1;
    private static final int CONTRIB_MENU = 1;
    private static final int CONTRIB_MENU_ITEM = 2;

    public static void contribute(MenuContribution menuContribution)
            throws ReflectiveOperationException {
        if (menuContribution == null)
            throw new NullPointerException("menuContribution");

        Class<?> contribClass = menuContribution.getClass();

        // Only fetch public fields.
        for (Field field : contribClass.getFields()) {

            int modifiers = field.getModifiers();

            // There should be other way to handle static contributions.
            // Maybe class-local cache?
            if (Modifier.isStatic(modifiers))
                continue;

            Class<?> fieldType = field.getType();

            int contribType = CONTRIB_N_A;
            if (IMenu.class.isAssignableFrom(fieldType))
                contribType = CONTRIB_MENU;
            else if (IMenuItem.class.isAssignableFrom(fieldType))
                contribType = CONTRIB_MENU_ITEM;
            else
                continue;

            Contribution contribAnn = field.getAnnotation(Contribution.class);
            if (contribAnn == null)
                continue;

            String targetPath = contribAnn.value();
            String targetSection = null;
            assert targetPath != null;

            int lastColon = targetPath.lastIndexOf(':');
            if (lastColon != -1) {
                targetSection = targetPath.substring(lastColon + 1);
                targetPath = targetPath.substring(0, lastColon);
            }

            Object fieldValue = field.get(menuContribution);
            switch (contribType) {
            case CONTRIB_MENU:

            case CONTRIB_MENU_ITEM:
            }

        }
    }

}
