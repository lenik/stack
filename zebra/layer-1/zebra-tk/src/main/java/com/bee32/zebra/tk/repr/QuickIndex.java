package com.bee32.zebra.tk.repr;

import net.bodz.bas.c.java.io.FilePath;
import net.bodz.bas.c.string.StringPred;
import net.bodz.bas.db.ibatis.IMapperTemplate;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.lily.model.base.CoObjectIndex;
import net.bodz.lily.model.base.Instantiables;

import com.bee32.zebra.tk.htm.IPageLayoutGuider;
import com.bee32.zebra.tk.htm.PageLayout;
import com.bee32.zebra.tk.sql.MapperUtil;

public abstract class QuickIndex
        extends CoObjectIndex
        implements IPageLayoutGuider {

    public QuickIndexFormat format = QuickIndexFormat.HTML;
    public boolean defaultPage = true;
    public PageLayout pageLayoutGuide = new PageLayout();

    public QuickIndex(IQueryable context) {
        super(context);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();
        IPathArrival ans = null;
        Object obj = null;

        switch (token) {
        case "new":
            try {
                obj = Instantiables._instantiate(getObjectType());
            } catch (Exception e) {
                throw new PathDispatchException(e.getMessage(), e);
            }
            ans = PathArrival.shift(previous, obj, tokens);
            break;

        default:
            String name = FilePath.stripExtension(token);
            if (StringPred.isDecimal(name)) {
                Long id = Long.parseLong(name);
                IMapperTemplate<?, ?> mapper = MapperUtil.getMapperTemplate(getObjectType());
                if (mapper == null)
                    throw new NullPointerException("mapperTemplate");
                obj = mapper.select(id);
                if (obj != null)
                    ans = PathArrival.shift(previous, obj, tokens);
            }
        }
        return ans;
    }

    @Override
    public void configure(PageLayout pageLayoutGuide) {
        switch (format) {
        case PICKER:
            pageLayoutGuide.setShowFrame(false);
            break;

        default:
        }
    }

}
