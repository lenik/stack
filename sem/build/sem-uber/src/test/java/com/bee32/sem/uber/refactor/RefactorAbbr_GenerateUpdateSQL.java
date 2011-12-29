package com.bee32.sem.uber.refactor;

import java.io.PrintStream;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.hibernate.cfg.NamingStrategy;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserEmail;
import com.bee32.icsf.principal.UserOption;
import com.bee32.icsf.principal.UserPreference;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.util.ITypeAbbrAware;

public class RefactorAbbr_GenerateUpdateSQL
        implements ITypeAbbrAware {

    PrintStream out = System.out;
    NamingStrategy namingStrategy = PloverNamingStrategy.INSTANCE;

    Map<String, StringBuilder> tableDdlMap = new LinkedHashMap<String, StringBuilder>();

    StringBuilder getTableDdl(String table) {
        StringBuilder ddl = tableDdlMap.get(table);
        if (ddl == null) {
            ddl = new StringBuilder();
            tableDdlMap.put(table, ddl);
        }
        return ddl;
    }

    public void refactor(String oldFqcn, String newFqcn)
            throws Exception {
        String oldAbbr = ABBR.abbr(oldFqcn);
        String newAbbr = ABBR.abbr(newFqcn);

        Map<Class<?>, String[]> usageMap = ABBR.loadUsageMap();

        for (Entry<Class<?>, String[]> entry : usageMap.entrySet()) {
            Class<?> entityType = entry.getKey();

            String entityName = entityType.getSimpleName();
            Entity _entity = entityType.getAnnotation(Entity.class);
            if (_entity != null) {
                String nameOverride = _entity.name();
                if (!nameOverride.isEmpty())
                    entityName = nameOverride;
            }
            String table = namingStrategy.tableName(entityName);

            StringBuilder ddl = getTableDdl(table);
            for (String property : entry.getValue()) {
                String abbrProperty = property + "Id";
                String abbrColumn = namingStrategy.columnName(abbrProperty);
                ddl.append(String.format("    update %s set %s='%s' where %s='%s';\n", //
                        table, //
                        abbrColumn, newAbbr, //
                        abbrColumn, oldAbbr));
            }
        } // for usageMap.entries
    }

    public static void main(String[] args)
            throws Exception {
        RefactorAbbr_GenerateUpdateSQL tool = new RefactorAbbr_GenerateUpdateSQL();

        Class<?>[] v = {
                //
                Principal.class, //
                User.class, //
                Group.class, //
                Role.class, //
                UserEmail.class, //
                UserOption.class, //
                UserPreference.class, //
        };
        for (Class<?> a : v) {
            String simpleName = a.getSimpleName();
            String oldFqcn = "com.bee32.plover.ox1.principal." + simpleName;
            String newFqcn = "com.bee32.icsf.principal." + simpleName;
            tool.refactor(oldFqcn, newFqcn);
        }

        for (Entry<String, StringBuilder> entry : tool.tableDdlMap.entrySet()) {
            String tableName = entry.getKey();
            String ddl = entry.getValue().toString();
            System.out.println("-- Update table: " + tableName);
            System.out.println(ddl);
        }
    }

}
