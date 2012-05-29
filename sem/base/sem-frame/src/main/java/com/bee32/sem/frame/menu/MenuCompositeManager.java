package com.bee32.sem.frame.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.service.ServicePrototypeLoader;

public class MenuCompositeManager {

    static List<Class<? extends MenuComposite>> mcClasses;
    static {
        try {
            mcClasses = new ArrayList<Class<? extends MenuComposite>>();
            for (Class<? extends MenuComposite> mcClass : ServicePrototypeLoader.load(MenuComposite.class, false)) {
                mcClasses.add(mcClass);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Class<? extends MenuComposite>> load() {
        return mcClasses;
    }

}
