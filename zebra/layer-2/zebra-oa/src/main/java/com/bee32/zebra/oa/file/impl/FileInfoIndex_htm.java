package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.impl.TagDefMask;
import net.bodz.lily.model.base.schema.impl.TagDefMapper;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.sys.TagGroups;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;
import com.bee32.zebra.tk.util.Listing;

public class FileInfoIndex_htm
        extends SlimIndex_htm<FileInfoIndex, FileInfo, FileInfoMask> {

    public FileInfoIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(FileInfoIndex.class);
        indexFields.parse("i*sa", "op", "label", "description", "dirName", "baseName", "size", "org", "person",
                "value", "tags", "downloads", "expireDate");
    }

    @Override
    protected FileInfoMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);
        FileInfoMask mask = MaskBuilder.fromRequest(new FileInfoMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("标签", true, //
                ctx.query(TagDefMapper.class).filter(TagDefMask.forTagGroup(TagGroups.WJXX)), //
                "tag", mask.tagId, mask.noTag);
        mask.tagId = sw.getSelection1();
        mask.noTag = sw.isSelectNull();

        sw = switchers.entryOf("年份", true, //
                mapper.histoByYear(), "year", mask.year, mask.noYear);
        mask.year = sw.getSelection1();
        mask.noYear = sw.isSelectNull();

        return mask;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<FileInfo> a, IUiRef<FileInfoIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);
        FileInfoMask mask = ctx.query(FileInfoMask.class);
        List<FileInfo> list = a.noList() ? null : postfilt(mapper.filter(mask));

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
    protected void userData(IHtmlViewContext ctx, IHtmlTag out, IUiRef<FileInfoIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        HtmlDivTag tmpl = out.div().id("child-0").class_("zu-template");
        GetFilePanel filePanel = new GetFilePanel(tmpl).class_("zu-detail");
        filePanel.setDirName("$segs");
        filePanel.setBaseName("$base");
        filePanel.setDescription("$description");
        filePanel.setHref("$href");
        filePanel.setBreadcrumb(false);
        filePanel.build();
    }

}
