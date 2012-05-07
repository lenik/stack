package com.bee32.sem.frame.report;

import java.io.File;
import java.util.List;

import javax.free.SystemProperties;

import net.sf.jasperreports.engine.util.JRProperties;

import com.bee32.plover.inject.AbstractActivatorService;
import com.bee32.plover.xutil.m2.UCLDumper;

public class JasperReportInitializer
        extends AbstractActivatorService {

    /**
     * The classpath used by the report compiler.
     * <p>
     * Defaults to <code>System.getProperty("java.class.path")</code>.
     *
     * @see JRProperties#COMPILER_CLASSPATH
     */
    static final String JASPERREPORTS_CCPATH = "net.sf.jasperreports.compiler.classpath";

    static final String PATH_SEP = SystemProperties.getPathSeparator();

    @Override
    public void activate() {
        ClassLoader ccl = getClass().getClassLoader();
        List<File> paths = UCLDumper.getLocalClasspaths(ccl);

        StringBuilder pathBuf = null;
        for (File path : paths) {
            if (pathBuf == null)
                pathBuf = new StringBuilder(paths.size() * 100);
            else
                pathBuf.append(PATH_SEP);
            pathBuf.append(path.getPath());
        }
        String classpath = pathBuf.toString();
        System.setProperty(JASPERREPORTS_CCPATH, classpath);
        // If JasperReports is already loaded, you may have to hack on the JRProperties.
        // JRProperties.setProperty(JASPERREPORTS_CCPATH, classpath);
    }

    public static void main(String[] args) {
        JasperReportInitializer a = new JasperReportInitializer();
        a.activate();
        String classpath = System.getProperty(JASPERREPORTS_CCPATH);
        System.out.println("Classpath: " + classpath);
    }

}
