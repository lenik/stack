package com.bee32.zebra.tk.sea;

import net.bodz.bas.db.ibatis.IMapper;
import net.bodz.bas.db.ibatis.IMapperTemplate;

import com.bee32.zebra.tk.sql.VhostDataService;

public class MapperUtil {

    @SuppressWarnings("unchecked")
    public static <T, C> IMapperTemplate<T, C> getMapperTemplate(Class<T> type) {
        Class<? extends IMapper> mapperClass = IMapper.fn.getMapperClass(type);
        IMapper mapper = VhostDataService.getInstance().query(mapperClass);
        return (IMapperTemplate<T, C>) mapper;
    }

}
