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
import java.util.Map.Entry;

import javax.free.BCharOut;
import javax.free.Boxing;
import javax.free.IPrintOut;
import javax.free.JavaioFile;
import javax.free.UnexpectedException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.mapping.Collection;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.entity.EmbeddablePiece;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.xutil.m2.MavenPath;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.ClassLibrary;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

public class EntityTXGen {

    PloverNamingStrategy namingStrategy = new PloverNamingStrategy();

    ClassLoader scl = ClassLoader.getSystemClassLoader();
    ClassLibrary syslib = new ClassLibrary(scl);
    JavaDocBuilder javaDocBuilder = new JavaDocBuilder(syslib);

    List<Class<?>> classes = new ArrayList<Class<?>>();
    Map<Class<?>, String> entityLabels = new HashMap<>();
    Map<Class<?>, String> entityDescriptions = new HashMap<>();

    public EntityTXGen()
            throws ClassNotFoundException, IOException {
        namingStrategy.setAlwaysEscape(false);

        for (Class<?> clazz : ServicePrototypeLoader.load(Entity.class, true)) {
            classes.add(clazz);
        }
    }

    public void run()
            throws Exception {
        Map<File, List<Class<?>>> projects = new HashMap<>();
        for (Class<?> clazz : classes) {
            File pomDir = generateTXFile(clazz);
            List<Class<?>> list = projects.get(pomDir);
            if (list == null) {
                list = new ArrayList<Class<?>>();
                projects.put(pomDir, list);
            }
            list.add(clazz);
        }

        // generate index for each project
        for (Entry<File, List<Class<?>>> project : projects.entrySet()) {
            File pomDir = project.getKey();
            File indexFile = new File(pomDir, "doc/TX-index.tex");
            List<Class<?>> list = project.getValue();
            generateIndex(indexFile, list);
        }
    }

    public void generateIndex(File indexFile, List<Class<?>> list)
            throws IOException {
        BCharOut out = new BCharOut();

        for (Class<?> entity : list) {
            String label = entityLabels.get(entity);
            String description = entityDescriptions.get(entity);
            String sqlname = namingStrategy.classToTableName(entity.getName());

            out.println("\\subsubsection{ \\nequiv " + label + " } {");
            out.println("    \\addtolength{\\leftskip}{5mm}");
            out.println("    " + description + "\n\n");
            out.println("    \\maketxtable{" + label + "}{" + sqlname + "}{TX." + entity.getSimpleName() + ".tab}");
            out.println("}");
        }

        String index = out.toString();
        new JavaioFile(indexFile).forWrite().write(index);
    }

    public File generateTXFile(Class<?> entity)
            throws IOException, IntrospectionException {
        // project/src/main/java/ com/...java
        File sourceFile = MavenPath.getSourceFile(entity);
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

        File txFile = new File(manualDir, "TX." + entity.getSimpleName() + ".tab");

        IPrintOut buf = new BCharOut();
        dump(buf, entity);
        String script = buf.toString();

        System.out.println("Write " + txFile);
        new JavaioFile(txFile).forWrite().write(script);

        return pomDir;
    }

    void dump(IPrintOut out, Class<?> clazz)
            throws IntrospectionException, IOException {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            if (Entity.class.isAssignableFrom(superclass))
                dump(out, superclass);
            else if (EmbeddablePiece.class.isAssignableFrom(superclass))
                dump(out, superclass);
        }

        ModelTemplate template = clazz.getAnnotation(ModelTemplate.class);

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        File sourceFile = MavenPath.getSourceFile(clazz);
        javaDocBuilder.addSource(sourceFile);
        JavaClass jclass = javaDocBuilder.getClassByName(clazz.getName());
        Map<String, JavaMethod> jmethodMap = new HashMap<>();
        for (JavaMethod jmethod : jclass.getMethods()) {
            jmethodMap.put(jmethod.getName(), jmethod);
        }

        Sections typedoc = Sections.parse(jclass.getComment());
        entityLabels.put(clazz, typedoc.get(0, "（无标题）"));
        entityDescriptions.put(clazz, typedoc.get(1, "（无描述）"));

        // Prepare the interesting property list.
        List<PropertyDescriptor> entityProperties = new ArrayList<PropertyDescriptor>();
        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            Method getter = property.getReadMethod();
            if (getter == null)
                continue;
            if (!getter.getDeclaringClass().equals(clazz))
                continue;
            if (getter.isAnnotationPresent(Transient.class))
                continue;
            if (getter.isAnnotationPresent(Embedded.class))
                dump(out, property.getPropertyType());
            entityProperties.add(property);
        }

        for (int propertyIndex = 0; propertyIndex < entityProperties.size(); propertyIndex++) {
            PropertyDescriptor property = entityProperties.get(propertyIndex);
            Method getter = property.getReadMethod();

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

            if (Date.class.isAssignableFrom(unboxed)) {
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
            }

            Basic _basic = getter.getAnnotation(Basic.class);
            if (_basic != null)
                nullable = _basic.optional();

            String name = property.getName();
            String sqlname = namingStrategy.columnName(name);

            String methodName = getter.getName();
            JavaMethod jmethod = jmethodMap.get(methodName);
            Sections methoddoc = Sections.parse(jmethod.getComment());

            String label = methoddoc.get(0, "（无名称）");
            String description = methoddoc.get(1, "（略）");

            if (nullable)
                flags += "o";
            if (template != null)
                flags += "t";

            String line = String.format("%s|%s|%s|%s|%s|%s|%d|%d|%s", //
                    propertyIndex == 0 ? clazz.getSimpleName() : "", // declared class name
                    propertyIndex == 0 ? entityProperties.size() : "", // group size
                    name, label, sqlname, //
                    flags, length, scale, //
                    description);

            out.println(line);
        }
    }

    public static void main(String[] args)
            throws Exception {
        new EntityTXGen().run();
    }

}

class Sections {

    List<String> sections = new ArrayList<>();

    public void add(String section) {
        sections.add(section);
    }

    public String get(int index, String defaultValue) {
        if (index >= sections.size())
            return defaultValue;
        else
            return sections.get(index);
    }

    public static Sections parse(String text) {
        Sections sections = new Sections();
        if (text != null) {
            text = text.trim();
            while (true) {
                int pos = text.indexOf("\n\n");
                if (pos == -1) {
                    sections.add(text);
                    break;
                } else {
                    String first = text.substring(0, pos);
                    sections.add(first);
                    text = text.substring(pos + 1).trim();
                }
            }
        }
        return sections;
    }

}
