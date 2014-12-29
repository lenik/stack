package com.bee32.zebra.tk.sea;

import net.bodz.bas.db.batis.IMapper;
import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.tk.sql.VhostDataService;

public class MapperUtil {

    @SuppressWarnings("unchecked")
    public static <T, C> IMapperTemplate<T, C> getMapperTemplate(Class<T> type) {
        String packageName = type.getPackage().getName();
        String mapperName = packageName + ".impl." + type.getSimpleName() + "Mapper";

        Class<? extends IMapper> mapperClass;
        try {
            mapperClass = (Class<? extends IMapper>) Class.forName(mapperName, true, type.getClassLoader());
        } catch (ClassNotFoundException e) {
            return null;
        }

        IMapper mapper = VhostDataService.getInstance().query(mapperClass);
        return (IMapperTemplate<T, C>) mapper;
    }

}
