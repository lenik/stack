package com.bee32.sem.frame.menu;

public class ContextMenuAssembler {

    static final IMenuAssembler globalAssembler = new DefaultMenuAssembler();
    static final ThreadLocal<IMenuAssembler> clAssemblers = new ThreadLocal<IMenuAssembler>();

    public static IMenuAssembler getMenuAssembler() {
        IMenuAssembler assembler = clAssemblers.get();
        if (assembler == null)
            assembler = globalAssembler;
        return assembler;
    }

    public static void setMenuAssembler(IMenuAssembler assembler) {
        clAssemblers.set(assembler);
    }

    public static void removeMenuAssembler() {
        clAssemblers.remove();
    }

}
