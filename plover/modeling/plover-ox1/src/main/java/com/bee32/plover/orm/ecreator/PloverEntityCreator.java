package com.bee32.plover.orm.ecreator;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.ClassResource;
import javax.free.URLResource;
import javax.free.UnexpectedException;
import javax.free.UnixStyleVarProcessor;

import com.bee32.plover.arch.type.FriendTypes;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.web.basic.BasicEntityController;

public class PloverEntityCreator {

    Class<? extends Entity<Serializable>> entityType;
    File javaDir;
    File resDir;

    Map<String, String> properties = new LinkedHashMap<String, String>();
    Set<String> isEntity = new HashSet<String>();
    Set<String> isList = new HashSet<String>();
    Set<String> isSet = new HashSet<String>();

    Map<String, String> vars = new HashMap<String, String>();

    void addType(Map<String, String> map, String prefix, String fqcn) {
        int dot = fqcn.lastIndexOf('.');
        assert dot != -1;
        String cn = fqcn.substring(dot + 1);
        String pkg = fqcn.substring(0, dot);
        map.put(prefix + "FQCN", fqcn);
        map.put(prefix + "FQPN", pkg);
        map.put(prefix + "CN", cn);
    }

    void addType(Map<String, String> map, String prefix, Class<?> type) {
        map.put(prefix, type.getName());
    }

    String apply(String extension, Map<String, String> map)
            throws IOException {
        URLResource resource = ClassResource.classData(getClass(), extension);
        String contents = resource.forRead().readTextContents();
        UnixStyleVarProcessor usvp = new UnixStyleVarProcessor(map);
        String result = usvp.process(contents);
        return result;
    }

    public void createDTOClass() {
        String javaName = entityType.getName().replace('.', '/') + "Dto.java";
        File file = new File(javaDir, javaName);
        System.out.println("Create DTO File: " + file);
    }

    public void createDAOClass() {
    }

    public void createBECClass() {
    }

    public void createIndexPage() {
    }

    public void createContentPage() {
    }

    public void createPage() {
    }

    public void run()
            throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(entityType, entityType.getSuperclass());

        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            String lcName = property.getName();

            String getter = property.getReadMethod().getName();
            String ucName;
            if (getter.startsWith("get"))
                ucName = getter.substring(3);
            else if (getter.startsWith("is"))
                ucName = getter.substring(2);
            else
                throw new UnexpectedException("Invalid getter method name: " + getter);

            properties.put(lcName, ucName);

            Class<?> propertyType = property.getPropertyType();

            if (Entity.class.isAssignableFrom(propertyType))
                isEntity.add(lcName);

            else if (List.class.isAssignableFrom(propertyType))
                isList.add(lcName);

            else if (Set.class.isAssignableFrom(propertyType))
                isSet.add(lcName);
        }

        addType(vars, "entity", entityType);
        addType(vars, "entityParent", entityType.getSuperclass());

        Class<?> keyType = EntityUtil.getKeyType(entityType);
        addType(vars, "key", keyType);

        Class<?> dtoParentType = EntityUtil.getDtoType(entityType.getSuperclass());
        Class<?> daoParentType = FriendTypes.getInheritedFriendType(entityType.getSuperclass(), "dao");
        addType(vars, "dtoParent", dtoParentType);
        addType(vars, "daoParent", daoParentType);
        addType(vars, "becParent", BasicEntityController.class);

        String dtoTypeName;
        String daoTypeName;
        String becTypeName;

        String entityPackage = entityType.getPackage().getName();
        if (entityPackage.endsWith(".entity")) {
            String pp = entityPackage.substring(0, entityPackage.length() - ".entity".length());
            dtoTypeName = pp + ".dto." + entityType.getSimpleName() + "Dto";
            daoTypeName = pp + ".dao." + entityType.getSimpleName() + "Dao";
            becTypeName = pp + ".web." + entityType.getSimpleName() + "Controller";
        } else {
            dtoTypeName = entityPackage + ".dto." + entityType.getSimpleName() + "Dto";
            daoTypeName = entityPackage + ".dao." + entityType.getSimpleName() + "Dao";
            becTypeName = entityPackage + ".web." + entityType.getSimpleName() + "Controller";
        }
        addType(vars, "dto", dtoTypeName);
        addType(vars, "dao", daoTypeName);
        addType(vars, "bec", becTypeName);

        createDTOClass();
        createDAOClass();
        createBECClass();

        createIndexPage();
        createContentPage();
        createPage();
    }

    public static void main(String[] args)
            throws Exception {
        if (args.length != 0 || args[0].equals("-h"))
            System.out.println("ecreator <Entity-Class>");

        String className = args[0];

        Class<? extends Entity<Serializable>> clazz = //
        (Class<? extends Entity<Serializable>>) Class.forName(className);

        if (!Entity.class.isAssignableFrom(clazz)) {
            System.err.println("Not an entity: " + clazz);
            return;
        }

        File java = new File("src/main/java");
        File res = new File("src/main/resources");

        if (!java.isDirectory()) {
            System.err.println("Not a directory: " + java);
            return;
        }

        if (!res.isDirectory()) {
            System.err.println("Not a directory: " + res);
            return;
        }

        PloverEntityCreator creator = new PloverEntityCreator();
        creator.entityType = clazz;
        creator.javaDir = java;
        creator.resDir = res;
        creator.run();
    }
}
