package com.bee32.zebra.tk.repr;

import net.bodz.bas.db.ctx.DataContext;
import net.bodz.bas.db.ibatis.IMapperTemplate;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.bas.site.vhost.VhostDataContexts;
import net.bodz.lily.model.base.CoObjectIndex;

import com.bee32.zebra.tk.htm.IPageLayoutGuider;
import com.bee32.zebra.tk.htm.PageLayout;

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
    protected IMapperTemplate<?, ?> findMapper(Class<?> clazz) {
        DataContext dataContext = VhostDataContexts.getInstance().forCurrentRequest();
        return dataContext.getMapperFor(getObjectType());
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
