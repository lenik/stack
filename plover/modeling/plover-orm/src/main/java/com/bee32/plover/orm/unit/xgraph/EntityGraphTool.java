package com.bee32.plover.orm.unit.xgraph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.CollectionType;
import org.hibernate.type.ManyToOneType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class EntityGraphTool {

    static Logger logger = LoggerFactory.getLogger(EntityGraphTool.class);

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

    @Inject
    CommonDataManager dataManager;

    PersistenceUnit unit;
    Map<Class<?>, EntityGraph> entityGraphMap;
    boolean built;

    public EntityGraphTool() {
        unit = SiteSessionFactoryBean.getForceUnit();
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

        for (Class<?> clazz : unit.getClasses()) {
            @SuppressWarnings("unchecked")
            Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) clazz;
            ClassMetadata metadata = sessionFactory.getClassMetadata(entityType);
            if (metadata == null)
                throw new IllegalUsageException("No metadata for entity type " + entityType.getSimpleName());
            String entityName = metadata.getEntityName();

            String[] columns = metadata.getPropertyNames();
            Type[] types = metadata.getPropertyTypes();
            assert columns.length == types.length;

            for (int i = 0; i < columns.length; i++) {
                String columnName = columns[i];
                Type columnType = types[i];
                if (!columnType.isAssociationType())
                    continue;

                Class<?> targetType = columnType.getReturnedClass();
                EntityGraph targetGraph = _getOrCreateEntityGraph(targetType);

                if (columnType instanceof ManyToOneType) {
                    targetGraph.manyToOne(//
                            entityType, // user table class
                            columnName, // user column name
                            columnType // user column type
                            );
                } else if (columnType instanceof CollectionType) {
                    @SuppressWarnings("unused")
                    CollectionType collType = (CollectionType) columnType;
                    // collType.getAssociatedEntityName(factory)
                    logger.debug(entityName + ":" + columnName + " => " + targetType);
                }
            }
        }

        // Create empty graph for entity types without any reference.
        for (Class<?> clazz : unit.getClasses())
            _getOrCreateEntityGraph(clazz);

        built = true;
    }

    @Override
    public String toString() {
        buildGraphs(sessionFactory);
        StringBuilder sb = new StringBuilder(entityGraphMap.size() * 512);
        for (EntityGraph graph : entityGraphMap.values()) {
            sb.append(graph);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Find all entities by which the specified entity is referenced.
     *
     * @param entity
     *            The key entity to find.
     * @return {@link EntityXrefMap} the referred entities are grouped by entity type.
     * @throws NullPointerException
     *             if given <code>entity</code> is <code>null</code>.
     */
    public EntityXrefMap getReferences(Entity<?> entity)
            throws DataAccessException {
        if (entity == null)
            throw new NullPointerException("entity");

        EntityXrefMap xrefMap = new EntityXrefMap();

        // search parent type?..
        Class<? extends Entity<?>> entityClass = (Class<? extends Entity<?>>) entity.getClass();
        EntityGraph graph = getEntityGraph(entityClass);

        for (EntityXrefMetadata xref : graph.getXrefs()) {
            // logger.debug(String.format("Search refs in %s : %s", //
            // xref.getEntityLabel(), xref.getPropertyName()));

            Class<? extends Entity<?>> targetType = xref.getEntityType();
            String fkProperty = xref.getPropertyName();

            Equals fkSelector = new Equals(fkProperty, entity);

            List<Entity<?>> list;
            try {
                IEntityAccessService<Entity<?>, Serializable> accessor = dataManager.asFor(targetType);
                list = accessor.list(fkSelector);
                sessionFactory.getCache().evictDefaultQueryRegion();
            } catch (Exception e) {
                logger.error("Failed to get xref-list on target " + targetType, e);
                continue;
            }

            EntityPartialRefs refList = new EntityPartialRefs(xref, list);
            xrefMap.put(xref, refList);
        }

        return xrefMap;
    }
}
