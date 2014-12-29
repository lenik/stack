package com.bee32.zebra.tk.sea;

import net.bodz.bas.c.string.StringPred;
import net.bodz.bas.db.batis.IMapperTemplate;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.tinylily.model.base.CoEntity;
import com.tinylily.repr.CoEntityManager;

public abstract class FooManager
        extends CoEntityManager {

    public FooManager(Class<? extends CoEntity> entityType, IQueryable context) {
        super(entityType, context);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        if (StringPred.isDecimal(token)) {
            Long id = Long.parseLong(token);
            IMapperTemplate<?, ?> mapper = MapperUtil.getMapperTemplate(getEntityType());
            if (mapper == null)
                throw new NullPointerException("mapperTemplate");
            Object obj = mapper.select(id);
            if (obj != null)
                return PathArrival.shift(previous, obj, tokens);
        }

        return super.dispatch(previous, tokens);
    }

}
