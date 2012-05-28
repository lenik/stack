package com.bee32.sem.frame.menu;

public class ContextMenuAssembler {

    static final ThreadLocal<IMenuAssembler> clAssemblers = new ThreadLocal<IMenuAssembler>();

    public static void setMenuAssembler(IMenuAssembler assembler) {
        clAssemblers.set(assembler);
    }

    public static void removeMenuAssembler() {
        clAssemblers.remove();
    }

}
