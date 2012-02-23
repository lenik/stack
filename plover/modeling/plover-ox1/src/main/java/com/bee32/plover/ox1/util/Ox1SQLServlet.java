package com.bee32.plover.ox1.util;

import javax.free.Doc;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
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
            if (template != null) {
                pre();
                for (Class<?> entityType : CustomizedSessionFactoryBean.getForceUnit().getClasses()) {
                    if (CEntity.class.isAssignableFrom(entityType)) {
                        String entityName = naming.classToTableName(entityType.getCanonicalName());
                        String sql = String.format(template, entityName);
                        text(sql);
                        text("\n");
                    }
                }
                end();
            }
        }

    }

}
