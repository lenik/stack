package com.bee32.plover.arch.ui;

public class UITraitsIndexCG {

    static Class<?>[] interfaceClasses = {
            //
            IImageMap.class, //
            IRefdocs.class, //
            IAppearance.class, //
    };

    public static void main(String[] args) {
        // Id-hash
        for (Class<?> iface : interfaceClasses) {
            String name = iface.getSimpleName();
            System.out.println("int traitsIndex = " + name.hashCode() + "; // " + name);
        }
        System.out.println();

        // Index-Map
        System.out.println("static final Map<Class<?>, Integer> commonTraitsIndex;");
        System.out.println("static {");
        System.out.println("    commonTraitsIndex = new HashMap<Class<?>, Integer>();");
        for (Class<?> iface : interfaceClasses) {
            String name = iface.getSimpleName();
            System.out.println("    commonTraitsIndex.put(" + name + ".class, " + name + ".traitsIndex);");
        }
        System.out.println("}");
        System.out.println();

        // Switch table
        for (Class<?> iface : interfaceClasses) {
            String name = iface.getSimpleName();
            System.out.println("    case " + name + ".traitsIndex: ");
        }
    }

}
