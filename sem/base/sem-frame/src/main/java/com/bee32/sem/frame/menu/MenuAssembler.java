package com.bee32.sem.frame.menu;

import java.io.IOException;

import javax.free.ClassLocal;

import com.bee32.plover.arch.service.ServicePrototypeLoader;

public class MenuAssembler
        implements IMenuAssembler {

    ClassLocal<MenuComposite> mcInstances;

    public MenuAssembler() {
        mcInstances = new ClassLocal<MenuComposite>();
    }

    @Override
    public <M extends MenuComposite> M require(Class<M> mcClass) {
        M mc = (M) mcInstances.get(mcClass);
        if (mc == null) {
            try {
                mc = mcClass.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            mcInstances.put(mcClass, mc);
        }
        return mc;
    }

    public void scan()
            throws ClassNotFoundException, IOException {
        for (Class<? extends MenuComposite> mcClass : ServicePrototypeLoader.load(MenuComposite.class, false)) {
            // profile parameters here...?
            require(mcClass);
        }
    }

}
