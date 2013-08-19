package com.bee32.sem.frame.menu;

import javax.free.ClassLocal;

/**
 * 缺省菜单汇编
 *
 * <p lang="en">
 * Default Menu Assembler
 */
public class DefaultMenuAssembler
        implements IMenuAssembler {

    ClassLocal<MenuComposite> mcInstances;

    public DefaultMenuAssembler() {
        mcInstances = new ClassLocal<MenuComposite>();
    }

    @Override
    public synchronized <M extends MenuComposite> M require(Class<M> mcClass) {
        M mc = (M) mcInstances.get(mcClass);
        if (mc == null) {
            try {
                // System.out.println("NEW: " + mcClass);
                mc = mcClass.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            mcInstances.put(mcClass, mc);
        }
        return mc;
    }

}
