package com.bee32.sem.uber.coll;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.BCharOut;
import javax.free.Boxing;
import javax.free.IPrintOut;
import javax.free.JavaioFile;
import javax.free.UnexpectedException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.mapping.Collection;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.xutil.m2.MavenPath;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.ClassLibrary;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

public class EntityTXGen {

    ClassLoader scl = ClassLoader.getSystemClassLoader();
    ClassLibrary syslib = new ClassLibrary(scl);
    JavaDocBuilder javaDocBuilder = new JavaDocBuilder(syslib);

    List<Class<?>> classes = new ArrayList<Class<?>>();

    public EntityTXGen()
            throws ClassNotFoundException, IOException {
        for (Class<?> clazz : ServicePrototypeLoader.load(Entity.class, true)) {
            classes.add(clazz);
        }
    }

    public void run()
            throws Exception {
        for (Class<?> clazz : classes)
            generateTXFile(clazz);
    }

    public void generateTXFile(Class<?> clazz)
            throws IOException, IntrospectionException {
        // project/src/main/java/ com/...java
        File sourceFile = MavenPath.getSourceFile(clazz);
        File pomDir = sourceFile.getParentFile();
        while (pomDir != null) {
            if (new File(pomDir, "pom.xml").exists())
                break;
            pomDir = pomDir.getParentFile();
        }
        if (pomDir == null)
            throw new UnexpectedException("Not in a maven project: " + sourceFile);

        File manualDir = new File(pomDir, "doc");
        manualDir.mkdirs();

        File txFile = new File(manualDir, "TX." + clazz.getSimpleName() + ".tab");

        IPrintOut buf = new BCharOut();
        dump(buf, clazz);
        String script = buf.toString();

        System.out.println("Write " + txFile);
        new JavaioFile(txFile).forWrite().write(script);
    }

    String parseLabel(String doc) {
        if (doc == null)
            return null;
        doc = doc.trim();
        String label;
        int sep = doc.indexOf("\n\n");
        if (sep == -1)
            label = doc;
        else
            label = doc.substring(0, sep).trim();
        return label;
    }

    String parseDescription(String doc) {
        if (doc == null)
            return null;
        doc = doc.trim();
        String description;
        int sep = doc.indexOf("\n\n");
        if (sep == -1)
            description = "";
        else
            description = doc.substring(sep + 1).trim();
        return description;
    }

    void dump(IPrintOut out, Class<?> clazz)
            throws IntrospectionException, IOException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && Entity.class.isAssignableFrom(superclass))
            dump(out, superclass);

        ModelTemplate template = clazz.getAnnotation(ModelTemplate.class);

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        String _label;
        String _description;
        // Map<String, String> labels = new HashMap<String, String>();
        // Map<String, String> descriptions = new HashMap<String, String>();

        File sourceFile = MavenPath.getSourceFile(clazz);
        javaDocBuilder.addSource(sourceFile);
        JavaClass jclass = javaDocBuilder.getClassByName(clazz.getName());
        Map<String, JavaMethod> jmethodMap = new HashMap<>();
        for (JavaMethod jmethod : jclass.getMethods()) {
            jmethodMap.put(jmethod.getName(), jmethod);
        }

        _label = parseLabel(jclass.getComment());
        _description = parseDescription(jclass.getComment());

        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            Method getter = property.getReadMethod();
            if (getter == null)
                continue;
            if (!getter.getDeclaringClass().equals(clazz))
                continue;
            if (getter.isAnnotationPresent(Transient.class))
                continue;

            Class<?> propertyType = property.getPropertyType();
            String flags = "N";
            int precision = 0;
            int scale = 0;

            Class<?> unboxed = Boxing.unbox(propertyType);
            if (unboxed.equals(String.class)) {
                precision = 255;
                scale = 0;
                flags = "S";
            } else if (unboxed == int.class) { // 4,294,967,296
                precision = 8; // 9min-10max;
            } else if (unboxed == boolean.class) { // 18 446 744 073 709 551 616
                precision = 1;
                flags = "B";
            } else if (unboxed == char.class) { // 18 446 744 073 709 551 616
                precision = 1;
                flags = "S"; // flags = "C";
            } else if (unboxed == long.class) { // 18 446 744 073 709 551 616
                precision = 18; // 19min-20max
            } else if (unboxed == byte.class) {
                precision = 2; // 2min-3max
            } else if (unboxed == short.class) {
                precision = 4; // 4min-5max
            } else if (unboxed == float.class) {
                precision = 6;
                scale = 0;
            } else if (unboxed == double.class) {
                precision = 15;
                scale = 0;
            } else if (unboxed.equals(BigDecimal.class)) {
                precision = 30;
                scale = 8;
            } else if (unboxed.equals(BigInteger.class)) {
                precision = 30;
                scale = 0;
            } else if (unboxed.equals(InputStream.class)) {
                precision = 4;
                scale = 0;
                flags = "Z"; // stream
            } else if (Date.class.isAssignableFrom(unboxed)) {
                precision = 8;
                Temporal _temporal = getter.getAnnotation(Temporal.class);
                if (_temporal != null)
                    switch (_temporal.value()) {
                    case DATE:
                        flags = "D";
                        break;
                    case TIME:
                        flags = "T";
                        break;
                    case TIMESTAMP:
                        flags = "DT";
                        break;
                    }
            } else if (Collection.class.isAssignableFrom(unboxed)) {
                flags = "R*"; // reference
            } else if (Entity.class.isAssignableFrom(unboxed)) {
                flags = "R";
            }

            int length = precision + scale;
            boolean nullable = unboxed.equals(propertyType);

            Column _column = getter.getAnnotation(Column.class);
            if (_column != null) {
                nullable = _column.nullable();
                precision = _column.precision();
                scale = _column.scale();
                length = _column.length();
                if (precision != 0) {
                    length = precision;
                    if (scale != 0)
                        length += 1 + scale;
                }
            }

            Basic _basic = getter.getAnnotation(Basic.class);
            if (_basic != null)
                nullable = _basic.optional();

            String name = property.getName();
            // String colname = name;
            // String label = labels.get(name);
            // String description = descriptions.get(name);

            String methodName = getter.getName();
            JavaMethod jmethod = jmethodMap.get(methodName);
            String label = parseLabel(jmethod.getComment());
            String description = parseDescription(jmethod.getComment());

            if (label == null)
                label = "";
            if (description == null)
                description = "";

            if (nullable)
                flags += "o";
            if (template != null)
                flags += "t";

            String line = String.format("%s|%s|%s|%d|%d|%s", //
                    name, flags, label, //
                    length, scale, //
                    description);

            out.println(line);
        }
    }

    public static void main(String[] args)
            throws Exception {
        new EntityTXGen().run();
    }

}
