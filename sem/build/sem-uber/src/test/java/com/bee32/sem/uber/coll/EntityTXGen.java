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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.free.BCharOut;
import javax.free.Boxing;
import javax.free.IPrintOut;
import javax.free.JavaioFile;
import javax.free.UnexpectedException;
import javax.free.Utf8ResourceBundle;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.mapping.Collection;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.xutil.m2.MavenPath;

public class EntityTXGen {

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

    void dump(IPrintOut out, Class<?> clazz)
            throws IntrospectionException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && Entity.class.isAssignableFrom(superclass))
            dump(out, superclass);

        ModelTemplate template = clazz.getAnnotation(ModelTemplate.class);

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        String _label;
        String _description;
        Map<String, String> labels = new HashMap<String, String>();
        Map<String, String> descriptions = new HashMap<String, String>();

        try {
            ResourceBundle bundle = Utf8ResourceBundle.getBundle(clazz.getName().replace('.', '/'));
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String property = bundle.getString(key).trim();
                if (property.isEmpty())
                    continue;
                if ("label".equals(key))
                    _label = property;
                else if ("description".equals(key))
                    _description = property;
                else if (key.endsWith(".label"))
                    labels.put(key.substring(0, key.length() - 6), property);
                else if (key.endsWith(".description"))
                    descriptions.put(key.substring(0, key.length() - 12), property);
            }
        } catch (MissingResourceException e) {
        }

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
            String label = labels.get(name);
            String description = descriptions.get(name);
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
