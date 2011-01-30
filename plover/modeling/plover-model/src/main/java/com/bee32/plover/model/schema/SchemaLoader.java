package com.bee32.plover.model.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeSet;

public class SchemaLoader {

    static ServiceLoader<SchemaBuilder> schemaBuilderLoader;
    static TreeSet<SchemaBuilder> schemaBuilders;

    static void reloadSchemaProviders() {
        schemaBuilderLoader = ServiceLoader.load(SchemaBuilder.class);
        schemaBuilders = new TreeSet<SchemaBuilder>(SchemaBuilderComparator.getInstance());
        for (SchemaBuilder provider : schemaBuilderLoader) {
            schemaBuilders.add(provider);
        }
    }

    private static Map<Class<?>, ISchema> schemaCache;
    static {
        // schemaCache = new WeakHashMap<Class<?>, ISchema>();
        schemaCache = new HashMap<Class<?>, ISchema>();
    }

    /**
     * Search and load the schema for a specific type.
     *
     * @return <code>null</code> If the schema isn't defined.
     * @throws SchemaBuilderException
     */
    public static ISchema loadSchema(Class<?> type)
            throws SchemaBuilderException {

        ISchema cachedSchema = (ISchema) schemaCache.get(type);

        if (cachedSchema == null) {
            for (SchemaBuilder builder : schemaBuilders) {
                ISchema schema = builder.buildSchema(type);
                cachedSchema = schema;
            }
        }

        return cachedSchema;
    }

}
