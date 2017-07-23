package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.bodz.bas.c.loader.ClassLoaders;
import net.bodz.bas.c.type.TypeIndex;
import net.bodz.bas.c.type.TypeParam;
import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperTemplate;
import net.bodz.bas.err.IllegalConfigException;

public class TableMetadataRegistry {

    ClassLoader classLoader = ClassLoaders.getRuntimeClassLoader();
    Map<Class<?>, TableMetadata> classMap;
    Map<String, TableMetadata> tableNameMap;

    public TableMetadataRegistry() {
        classMap = new HashMap<>();
        tableNameMap = new HashMap<>();
        try {
            findFromMappers();
        } catch (Exception e) {
            throw new IllegalConfigException(e.getMessage(), e);
        }
    }

    void findFromMappers()
            throws ClassNotFoundException, IOException {
        TypeIndex typeIndex = TypeIndex.getInstance(classLoader);

        for (Class<?> mapperClass : typeIndex.listIndexed(IMapper.class)) {
            if (!IMapperTemplate.class.isAssignableFrom(mapperClass))
                continue;
            Class<?> entityClass = TypeParam.infer1(mapperClass, IMapperTemplate.class, 0);
            register(entityClass);
        }
    }

    public void register(Class<?> entityClass) {
        TableMetadata metadata = new TableMetadata(entityClass);
        classMap.put(entityClass, metadata);
        tableNameMap.put(metadata.getName(), metadata);
    }

    public TableMetadata get(Class<?> entityClass) {
        return classMap.get(entityClass);
    }

    public TableMetadata get(String tableName) {
        return tableNameMap.get(tableName);
    }

    private static TableMetadataRegistry instance = new TableMetadataRegistry();

    public static TableMetadataRegistry getInstance() {
        return instance;
    }

}
