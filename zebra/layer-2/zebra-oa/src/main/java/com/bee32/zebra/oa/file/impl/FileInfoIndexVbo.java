package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlTdTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.sys.TagSets;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.bee32.zebra.tk.util.Listing;
import com.tinylily.model.base.schema.impl.TagDefCriteria;
import com.tinylily.model.base.schema.impl.TagDefMapper;

public class FileInfoIndexVbo
        extends Zc3Template_CEM<FileInfoIndex, FileInfo> {

    public FileInfoIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FileInfoIndex.class);
        indexFields.parse("i*sa", "op", "label", "description", "dirName", "baseName", "size", "org", "person",
                "value", "tags", "downloads", "expireDate");
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<FileInfo> a, IUiRef<FileInfoIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);

        FileInfoCriteria criteria = fn.criteriaFromRequest(new FileInfoCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("标签", true, //
                    ctx.query(TagDefMapper.class).filter(TagDefCriteria.forTagSet(TagSets.WJXX)), //
                    "tag", criteria.tagId, criteria.noTag);
            criteria.tagId = so.key;
            criteria.noTag = so.isNull;

            so = filters.switchPairs("年份", true, //
                    mapper.histoByYear(), "year", criteria.year, criteria.noYear);
            criteria.year = so.key;
            criteria.noYear = so.isNull;
        }

        List<FileInfo> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.addDetailFields("description", "dirName", "baseName", "tags", "downloads");
        itab.buildHeader(ctx, indexFields.values());

        if (a.dataList())
            for (FileInfo o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);

                ref(tr.td(), o.getOp()).align("center");
                itab.cocols("u", tr, o);
                tr.td().text(o.getDirName()).class_("small");
                tr.td().text(o.getBaseName()).class_("small");
                tr.td().text(o.getSize()).class_("small");
                ref(tr.td(), o.getOrg()).class_("small");
                ref(tr.td(), o.getPerson()).class_("small");
                tr.td().text(o.getValue()).style("font-weight: bold");
                tr.td().text(Listing.joinLabels(", ", o.getTags())).class_("small");
                tr.td().text(o.getDownloads());
                tr.td().text(fmt.formatDate(o.getExpireDate()));

                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

    @Override
    protected void afterData(IHtmlViewContext ctx, IHtmlTag out, IUiRef<FileInfoIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        out = out.div().id("child-0").class_("zu-template");
        HtmlTrTag tr = out.table().class_("zu-detail").tr();
        HtmlTdTag left = tr.td().valign("top").style("width: 4em");
        left.div().align("center").i().class_("fa fa-2x").text(FA_FILE_O);
        left.hr();

        HtmlATag downloadLink = left.span().a().href("$href?mode=attachment");
        downloadLink.i().class_("fa").text(FA_DOWNLOAD);
        downloadLink.text("下载");

        HtmlTdTag right = tr.td().valign("top");
        HtmlATag fileLink = right.div().a().href("$href");
        fileLink.i().class_("fa").text(FA_EXTERNAL_LINK);
        fileLink.b().text("$base");

        right.div().ol().class_("breadcrumb").text("$segs");
        right.div().text("$description");

        // right.hr();
        // right.div().text("下载次数：$downloads");
    }

}
