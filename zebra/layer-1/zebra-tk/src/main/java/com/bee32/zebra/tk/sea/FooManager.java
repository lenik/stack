package com.bee32.zebra.tk.sea;

import net.bodz.bas.c.string.StringPred;
import net.bodz.bas.db.batis.IMapperTemplate;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;

import com.tinylily.model.base.CoEntity;
import com.tinylily.repr.CoEntityManager;

public abstract class FooManager
        extends CoEntityManager {

    public FooManager(Class<? extends CoEntity> entityType) {
        super(entityType);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        if (StringPred.isDecimal(token)) {
            Long id = Long.parseLong(token);
            IMapperTemplate<?, ?> mapperTemplate = MapperUtil.getMapperTemplate(entityType);
            if (mapperTemplate == null)
                throw new NullPointerException("mapperTemplate");
            Object obj = mapperTemplate.select(id);
            if (obj != null)
                return PathArrival.shift(previous, obj, tokens);
        }

        return super.dispatch(previous, tokens);
    }

}
