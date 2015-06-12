package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.impl.TagDefCriteria;
import net.bodz.lily.model.base.schema.impl.TagDefMapper;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.sys.TagSets;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;
import com.bee32.zebra.tk.util.Listing;

public class FileInfoIndexVbo
        extends SlimIndex_htm<FileInfoIndex, FileInfo, FileInfoCriteria> {

    public FileInfoIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(FileInfoIndex.class);
        indexFields.parse("i*sa", "op", "label", "description", "dirName", "baseName", "size", "org", "person",
                "value", "tags", "downloads", "expireDate");
    }

    @Override
    protected FileInfoCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);
        FileInfoCriteria criteria = CriteriaBuilder.fromRequest(new FileInfoCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("标签", true, //
                ctx.query(TagDefMapper.class).filter(TagDefCriteria.forTagSet(TagSets.WJXX)), //
                "tag", criteria.tagId, criteria.noTag);
        criteria.tagId = sw.getSelection1();
        criteria.noTag = sw.isSelectNull();

        sw = switchers.entryOf("年份", true, //
                mapper.histoByYear(), "year", criteria.year, criteria.noYear);
        criteria.year = sw.getSelection1();
        criteria.noYear = sw.isSelectNull();

        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<FileInfo> a, IUiRef<FileInfoIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);
        FileInfoCriteria criteria = ctx.query(FileInfoCriteria.class);
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
    protected void afterData(IHttpViewContext ctx, IHtmlTag out, IUiRef<FileInfoIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        HtmlDivTag tmpl = out.div().id("child-0").class_("zu-template");

        HtmlTrTag tr = tmpl.table().class_("zu-detail").tr();
        HtmlTdTag left = tr.td().valign("top").style("width: 4em");
        left.div().align("center").i().class_("fa fa-2x").text(FA_FILE_O);
        left.hr();

        HtmlATag downloadLink = left.span().a().href("$href?mode=attachment");
        downloadLink.i().class_("fa").text(FA_DOWNLOAD);
        downloadLink.text("下载");

        HtmlTdTag right = tr.td().valign("top");
        HtmlATag fileLink = right.div().a().href("_blank", "$href");
        fileLink.i().class_("fa").text(FA_EXTERNAL_LINK);
        fileLink.b().text("$base");

        right.div().ol().class_("breadcrumb").text("$segs");
        right.div().text("$description");

        // right.hr();
        // right.div().text("下载次数：$downloads");
    }

}
