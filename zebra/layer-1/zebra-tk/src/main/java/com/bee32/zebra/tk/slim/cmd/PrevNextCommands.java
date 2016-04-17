package com.bee32.zebra.tk.slim.cmd;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.db.meta.TableUtils;
import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.bas.ui.model.cmd.AbstractCommandProvider;
import net.bodz.bas.ui.model.cmd.ICommand;
import net.bodz.lily.model.base.CoEntity;

import com.bee32.zebra.tk.sql.FnMapper;
import com.bee32.zebra.tk.util.PrevNext;

class PrevNextCommands
        extends AbstractCommandProvider {

    /**
     * 前滚翻
     * 
     * @cmd.href "../" + prev + "/"
     */
    @FontAwesomeImage(IFontAwesomeCharAliases.FA_CHEVRON_CIRCLE_LEFT)
    class PrevCommand
            extends UiServletCommand {

    }

    /**
     * 后滚翻
     * 
     * @cmd.href "../" + next + "/"
     */
    @FontAwesomeImage(IFontAwesomeCharAliases.FA_CHEVRON_CIRCLE_RIGHT)
    class NextCommand
            extends UiServletCommand {

    }

    IQueryable ctx;

    @Override
    public List<ICommand> getCommands(Object o) {
        Class<?> type = o.getClass();
        CoEntity<?> obj = (CoEntity<?>) o;
        Number id = (Number) obj.getId();

        String tablename = TableUtils.tablename(type);
        FnMapper fnMapper = ctx.query(FnMapper.class);

        // FIXME consider access control and mask.
        PrevNext prevNext = fnMapper.prevNext("public", tablename, //
                id == null ? Integer.MAX_VALUE : id.longValue());
        Long prev = prevNext == null ? null : prevNext.getPrev();
        Long next = prevNext == null ? null : prevNext.getNext();

        List<ICommand> list = new ArrayList<>();
        return list;
    }
}