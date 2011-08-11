package com.bee32.plover.sysutils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class DebugUtil {

    public static boolean isDebugging() {
        RuntimeMXBean runtimeMx = ManagementFactory.getRuntimeMXBean();
        List<String> args = runtimeMx.getInputArguments();
        String cmdline = args.toString();
        int jdwp = cmdline.indexOf("-agentlib:jdwp");
        return jdwp != -1;
    }

    public static void main(String[] args) {
        System.out.println("Debugging: " + isDebugging());
    }

}
