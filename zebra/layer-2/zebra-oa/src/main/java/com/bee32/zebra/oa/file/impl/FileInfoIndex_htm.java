package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.impl.TagDefMapper;
import net.bodz.lily.model.base.schema.impl.TagDefMask;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.sys.TagGroups;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.Listing;
import com.bee32.zebra.tk.util.MaskBuilder;

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
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfoIndex> ref)
            throws ViewBuilderException, IOException {
        FileInfoMapper mapper = ctx.query(FileInfoMapper.class);
        FileInfoMask mask = ctx.query(FileInfoMask.class);
        List<FileInfo> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        itab.addDetailFields("description", "dirName", "baseName", "tags", "downloads");
        HtmlTbody tbody = itab.buildViewStart(out);

        for (FileInfo o : list) {
            HtmlTr tr = tbody.tr();
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
        itab.buildViewEnd(tbody);
        return list;
    }

    @Override
    protected void userData(IHtmlViewContext ctx, IHtmlOut out, IUiRef<FileInfoIndex> ref)
            throws ViewBuilderException, IOException {
        HtmlDiv tmpl = out.div().id("child-0").class_("zu-template");
        GetFilePanel filePanel = new GetFilePanel();
        filePanel.class_ = "zu-detail";
        filePanel.dirName = "$segs";
        filePanel.baseName = "$base";
        filePanel.description = "$description";
        filePanel.href = "$href";
        filePanel.breadcrumb = false;
        filePanel.build(tmpl);
    }

}
