package com.bee32.plover.ox1.util;

import java.util.Collections;
import java.util.Set;

import javax.free.Doc;
import javax.persistence.Entity;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.site.SimpleServlet;

public class Ox1SQLServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public Ox1SQLServlet() {
        pages.add("c", CEntityHelper.class);
    }

    /**
     * .../sql/c?template=
     */
    @Doc("SQL For All CEntity")
    public static class CEntityHelper
            extends HtmlTemplate {
        @Override
        protected void _pageContent()
                throws Exception {
            PloverNamingStrategy naming = PloverNamingStrategy.getDefaultInstance();

            String template = getRequest().getParameter("template");
            String unitName = getRequest().getParameter("unit");

            if (template != null) {
                pre();

                PersistenceUnit root = SiteSessionFactoryBean.getForceUnit();
                Set<Class<?>> selection = Collections.emptySet();
                if (unitName == null) {
                    selection = root.getClasses();
                } else {
                    for (ClassCatalog catalog : root.getAllDependencies()) {
                        if (unitName.equals(catalog.getName())) {
                            selection = catalog.getLocalClasses();
                            break;
                        }
                    }
                }

                for (Class<?> entityType : selection) {
                    if (!CEntity.class.isAssignableFrom(entityType))
                        continue;

                    Class<?> anySuper = entityType.getSuperclass();
                    boolean inherited = false;
                    while (anySuper != null) {
                        if (anySuper.getAnnotation(Entity.class) != null) {
                            inherited = true;
                            break;
                        }
                        anySuper = anySuper.getSuperclass();
                    }
                    if (inherited)
                        continue;

                    String entityName = naming.classToTableName(entityType.getCanonicalName());
                    entityName = entityName.replace("`", "\""); // PL/SQL.
                    String sql = String.format(template, entityName);
                    text(sql);
                    text(";\n");
                }
                end();
            }
        }
    }

}
