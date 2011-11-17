package com.bee32.plover.orm.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.ManyToOneType;
import org.hibernate.type.Type;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Component
@Lazy
public class EntityGraphTool {

    /**
     * This will inject the default (site) session factory, however, it's just enough.
     *
     * Cuz we don't care about the real data in the graph.
     *
     * Warning: The scope-proxy is used, and null-request must be allowed.
     *
     * @see ThreadHttpContext#allowNullRequest
     */
    @Inject
    SessionFactory sessionFactory;

    PersistenceUnit unit;
    Map<Class<?>, EntityGraph> entityGraphMap;
    boolean built;

    public EntityGraphTool() {
        unit = CustomizedSessionFactoryBean.getForceUnit();
        entityGraphMap = new HashMap<Class<?>, EntityGraph>();
    }

    public EntityGraph getEntityGraph(Class<?> entityType) {
        buildGraphs(sessionFactory);
        EntityGraph entityGraph = entityGraphMap.get(entityType);
        return entityGraph;
    }

    private EntityGraph _getOrCreateEntityGraph(Class<?> entityType) {
        EntityGraph entityGraph = entityGraphMap.get(entityType);
        if (entityGraph == null) {
            entityGraph = new EntityGraph(unit, entityType);
            entityGraphMap.put(entityType, entityGraph);
        }
        return entityGraph;
    }

    /**
     * @param sessionFactory
     *            Any session factory for analyzing purpose.
     */
    synchronized void buildGraphs(SessionFactory sessionFactory) {
        if (built)
            return;

        Set<Class<?>> entityTypes = unit.getClasses();
        for (Class<?> entityType : entityTypes) {
            ClassMetadata metadata = sessionFactory.getClassMetadata(entityType);
            String entityName = metadata.getEntityName();

            String[] columns = metadata.getPropertyNames();
            Type[] types = metadata.getPropertyTypes();
            assert columns.length == types.length;

            for (int i = 0; i < columns.length; i++) {
                String column = columns[i];
                Type type = types[i];
                if (!type.isAssociationType())
                    continue;

                boolean manyToOne = type instanceof ManyToOneType;

                Class<?> targetType = type.getReturnedClass();
                System.out.println(entityName + ":" + column + " => " + targetType);

                EntityGraph targetGraph = _getOrCreateEntityGraph(targetType);
                targetGraph.manyToOne(//
                        entityName, // user table name
                        entityType, // user table class
                        column // user column
                        );
            }
        }

        built = true;
    }

}
