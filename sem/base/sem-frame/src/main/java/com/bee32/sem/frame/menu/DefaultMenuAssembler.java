package com.bee32.sem.frame.menu;

import javax.free.ClassLocal;

public class DefaultMenuAssembler
        implements IMenuAssembler {

    ClassLocal<MenuComposite> mcInstances;

    public DefaultMenuAssembler() {
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

}
