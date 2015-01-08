package com.bee32.zebra.tk.sea;

import net.bodz.bas.c.string.StringPred;
import net.bodz.bas.db.batis.IMapperTemplate;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.CoObjectIndex;

public abstract class FooIndex
        extends CoObjectIndex {

    public FooIndex(IQueryable context) {
        super(context);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        switch (token) {
        case "new":
            CoObject obj;
            try {
                obj = (CoObject) getObjectType().newInstance();
            } catch (Exception e) {
                throw new PathDispatchException(e.getMessage(), e);
            }
            return PathArrival.shift(previous, obj, tokens);
        }

        if (StringPred.isDecimal(token)) {
            Long id = Long.parseLong(token);
            IMapperTemplate<?, ?> mapper = MapperUtil.getMapperTemplate(getObjectType());
            if (mapper == null)
                throw new NullPointerException("mapperTemplate");
            Object obj = mapper.select(id);
            if (obj != null)
                return PathArrival.shift(previous, obj, tokens);
        }

        return null;
    }

}
