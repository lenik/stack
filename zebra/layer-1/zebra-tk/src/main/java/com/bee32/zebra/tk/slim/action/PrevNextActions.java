package com.bee32.zebra.tk.slim.action;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.db.meta.TableUtils;
import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.bas.ui.model.action.AbstractActionProvider;
import net.bodz.bas.ui.model.action.IAction;
import net.bodz.lily.model.base.CoEntity;

import com.bee32.zebra.tk.sql.FnMapper;
import com.bee32.zebra.tk.util.PrevNext;

class PrevNextActions
        extends AbstractActionProvider {

    /**
     * 前滚翻
     */
    @FontAwesomeImage(IFontAwesomeCharAliases.FA_CHEVRON_CIRCLE_LEFT)
    class PrevAction
            extends UiScriptAction {

        {
            addScript("href");
        }

        @Override
        public String getScript(String scriptId, Object obj) {
            switch (scriptId) {
            case "href":
                String prev = "TODO";
                return "../" + prev + "/";
            }
            return super.getScript(scriptId, obj);
        }

    }

    /**
     * 后滚翻
     */
    @FontAwesomeImage(IFontAwesomeCharAliases.FA_CHEVRON_CIRCLE_RIGHT)
    public static class NextAction
            extends UiScriptAction {

        {
            addScript("href");
        }

        @Override
        public String getScript(String scriptId, Object obj) {
            switch (scriptId) {
            case "href":
                String next = "TODO";
                return "../" + next + "/";
            }
            return super.getScript(scriptId, obj);
        }

    }

    IQueryable ctx;

    @Override
    public List<IAction> getActions(Object _obj) {
        Class<?> type = _obj.getClass();
        CoEntity<?> obj = (CoEntity<?>) _obj;
        Number id = (Number) obj.getId();

        String tablename = TableUtils.tablename(type);
        FnMapper fnMapper = ctx.query(FnMapper.class);

        // FIXME consider access control and mask.
        PrevNext prevNext = fnMapper.prevNext("public", tablename, //
                id == null ? Integer.MAX_VALUE : id.longValue());
        Long prev = prevNext == null ? null : prevNext.getPrev();
        Long next = prevNext == null ? null : prevNext.getNext();

        List<IAction> list = new ArrayList<>();
        return list;
    }
}