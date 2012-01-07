package com.bee32.plover.orm.annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.orm.entity.Entity;

public class ForEntityUtil {

    public static List<EntityTypePattern> getEntitTypePatterns(Class<?> clazz) {
        List<EntityTypePattern> patterns = new ArrayList<EntityTypePattern>();

        ForEntities forEntities = clazz.getAnnotation(ForEntities.class);
        if (forEntities != null)
            for (ForEntity forEntity : forEntities.value()) {
                parseAnnotation(forEntity, patterns);
            }

        ForEntity forEntity = clazz.getAnnotation(ForEntity.class);
        if (forEntity != null)
            parseAnnotation(forEntity, patterns);

        return patterns;
    }

    static void parseAnnotation(ForEntity forEntity, List<EntityTypePattern> patterns) {
        Class<? extends Entity<?>> entityType = forEntity.value();
        TypeParameter[] _parameters = forEntity.parameters();
        if (_parameters.length == 0) {
            EntityTypePattern pattern = new EntityTypePattern(entityType);
            patterns.add(pattern);
        } else {
            for (Map<String, String> parameters : new Permutator(_parameters).permute()) {
                EntityTypePattern pattern = new EntityTypePattern(entityType, parameters);
                patterns.add(pattern);
            }
        }
    }

    static class Permutator {

        TypeParameter[] params;
        Map<String, String> template = new HashMap<String, String>();
        List<Map<String, String>> result;

        public Permutator(TypeParameter[] params) {
            if (params == null)
                throw new NullPointerException("params");
            this.params = params;
        }

        public synchronized List<Map<String, String>> permute() {
            template.clear();
            result = new ArrayList<Map<String, String>>();
            permute(0);
            return result;
        }

        void permute(int paramIndex) {
            if (paramIndex == params.length) {
                result.add(copy());
                return;
            }
            TypeParameter param = params[paramIndex++];
            String name = param.name();
            String[] values = param.value();
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                template.put(name, value);
                permute(paramIndex);
            }
        }

        Map<String, String> copy() {
            return new LinkedHashMap<String, String>(template);
        }

    }

}
