package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.orm.util.Accessors;

public class EntityDaoUtil {

    /**
     * Find DAO class using heuristic alg.
     *
     * @return Non-<code>null</code> DAO accessor type.
     * @throws IllegalUsageException
     *             If no DAO class is found.
     */
    public static <Dao extends EntityDao<E, K>, E extends Entity<K>, K extends Serializable> Class<Dao> getDaoTypeHeuristic(
            Class<E> entityType) {

        if (entityType == null)
            throw new NullPointerException("entityType");

        Accessors accessorsAnn = entityType.getAnnotation(Accessors.class);
        if (accessorsAnn != null) {
            @SuppressWarnings("unchecked")
            Class<Dao> accessor1 = (Class<Dao>) accessorsAnn.value()[0];
            return accessor1;
        }

        String entity_Name = entityType.getName();

        // *Dao
        // *.{.entity. => .dao.}.*Dao

        String entity_NameDao = entity_Name + "Dao";
        try {
            return (Class<Dao>) Class.forName(entity_NameDao);
        } catch (ClassNotFoundException e) {

            int lastEntityPackage = entity_Name.lastIndexOf(".entity.");
            String daoPackage;
            if (lastEntityPackage != -1) {
                String parentPackage = entity_Name.substring(0, lastEntityPackage);
                daoPackage = parentPackage + ".dao.";
            } else {
                int lastDot = entity_Name.lastIndexOf('.');
                String parentPackage = entity_Name.substring(0, lastDot);
                daoPackage = parentPackage + ".dao.";
            }

            String simpleName = entityType.getSimpleName();
            String dao_NameDao = daoPackage + simpleName + "Dao";

            try {
                return (Class<Dao>) Class.forName(dao_NameDao);
            } catch (ClassNotFoundException e1) {
            }
        }

        // All faileds.

        String message = "The default accessor for entity type " + entity_Name + " is unknown, " + //
                "(You can set it with the @Accessors annotation, or name the Dao classes in convention name.)";
        throw new IllegalUsageException(message);
    }

}
