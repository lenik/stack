package com.bee32.zebra.io.sales.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.c.java.util.regex.UnixStyleVarExpander;
import net.bodz.bas.c.loader.ClassResource;
import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.err.IllegalUsageException;
import net.bodz.bas.err.TransformException;
import net.bodz.bas.fn.ITransformer;
import net.bodz.bas.http.viz.IHttpViewContext;
import net.bodz.bas.io.BCharOut;
import net.bodz.bas.io.IPrintOut;
import net.bodz.bas.io.res.builtin.URLResource;
import net.bodz.bas.io.res.tools.StreamReading;
import net.bodz.bas.pdf.viz.TexPdfViewBuilder;
import net.bodz.bas.repr.req.RequestUtils;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.Delivery;
import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Party;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class Delivery_pdf
        extends TexPdfViewBuilder<Delivery>
        implements IZebraSiteAnchors {

    public Delivery_pdf() {
        super(Delivery.class);
    }

    @Override
    public void buildTex(IHttpViewContext ctx, IPrintOut out, IUiRef<Delivery> ref, IOptions options)
            throws ViewBuilderException, IOException {
        URLResource data = ClassResource.getData(getClass(), "tex");
        final String texTemplate = data.to(StreamReading.class).readString();

        final HttpServletRequest req = ctx.getRequest();
        final String thisUrl = req.getRequestURL().toString();
        final FileManager fileManager = FileManager.forCurrentRequest();

        final Delivery doc = ref.get();
        final Person person = doc.getPerson();
        final Organization org = doc.getOrg();
        final Party party = person != null ? person : org;

        UnixStyleVarExpander expander = new UnixStyleVarExpander(new ITransformer<String, Object>() {

            DecimalFormat qtyFormat = new DecimalFormat("#,##0.##");
            DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

            @Override
            public Object transform(String input)
                    throws TransformException {
                BCharOut out = new BCharOut();
                StringBuilder sb = new StringBuilder();
                switch (input) {
                case "Head":
                    out.println("\\Huge 送货单\\\\");
                    out.println("海宁中鑫三元风机有限公司 \\\\");
                    out.println("\\\\");
                    sb.append("\\#" + doc.getId() + "; ");
                    sb.append(Dates.YYYY_MM_DD.format(doc.getBeginDate()) + "; ");
                    sb.append(party.getLabel());
                    Contact contact0 = party.getContact0();
                    if (contact0 != null) {
                        sb.append(" (");
                        contact0.getTels();
                        sb.append(")");
                    }
                    sb.append(".");
                    out.println(sb);
                    break;

                case "Info":
                    out.println(doc.getSubject());
                    out.println("\\\\");

                    String text = doc.getText();
                    if (!Nullables.isEmpty(text)) {
                        out.println();
                        out.println(text);
                        out.println("\\\\");
                    }

                    Contact shipDest = doc.getShipDest();
                    Organization shipper = doc.getShipper();
                    if (shipDest != null) {
                        out.println("送货地址：" + shipDest.getFullAddress() + "\\\\");
                        out.println("承运人: " + shipper.getLabel() + "\\\\");
                        out.println("运单号: " + doc.getShipmentId() + "\\\\");
                        out.println("运费: " + doc.getShippingCost() + "\\\\");
                    }
                    break;

                case "Logo":
                    File logoFile = new File(fileManager.getStartDir(), "logo.png");
                    return logoFile.getPath();

                case "QRThis":
                    return thisUrl;

                case "QRCustomer":
                    String surl = RequestUtils.getServerURL(req);
                    if (person != null)
                        return surl + "person/" + person.getId() + "/";
                    if (org != null)
                        return surl + "org/" + org.getId() + "/";
                    break;

                case "Data":
                    double qtyTotal = 0;
                    double totalTotal = 0;
                    for (DeliveryItem ditem : doc.getItems()) {
                        sb.setLength(0);
                        sb.append(ditem.getId());

                        SalesOrderItem item = ditem.getSalesOrderItem();
                        Artifact art = item.getArtifact();
                        String label = art.getLabel();
                        String altLabel = item.getAltLabel();
                        String altSpec = item.getAltSpec();
                        if (art.getId() == 0 || label.isEmpty()) {
                            if (!Nullables.isEmpty(altLabel)) {
                                label = altLabel;
                                altLabel = null;
                            }
                        }
                        sb.append("& " + label);
                        sb.append("& " + qtyFormat.format(ditem.getQuantity()));
                        sb.append("& " + art.getUom().getLabel());
                        sb.append("& " + priceFormat.format(ditem.getPrice()));
                        sb.append("& " + priceFormat.format(ditem.getTotal()));
                        qtyTotal += ditem.getQuantity();
                        totalTotal += ditem.getTotal();

                        sb.append("& " + Nullables.coalesce(item.getComment(), ""));
                        sb.append("& " + Nullables.coalesce(item.getFootnote(), ""));
                        sb.append("\\\\");
                        out.println(sb);

                        if (!Nullables.isEmpty(altLabel) || !Nullables.isEmpty(altSpec)) {
                            sb.setLength(0);
                            sb.append("& \\multicolumn{7}{l}{");
                            if (!Nullables.isEmpty(altLabel))
                                sb.append("{\\footnotesize 定制名称: " + altLabel + "}");
                            if (!Nullables.isEmpty(altSpec))
                                sb.append(", {\\footnotesize 规格: " + altSpec + "}");
                            sb.append("}\\\\");
                            out.println(sb);
                        }
                    }
                    out.println("\\midrule");
                    out.println("& \\textit{小结} & " + //
                            qtyFormat.format(qtyTotal) + " & &  & " + //
                            priceFormat.format(totalTotal) + " & &");
                    break;

                case "Tail":
                    String comment = doc.getComment();
                    if (!Nullables.isEmpty(comment)) {
                        out.println("备注: " + comment + "\n");
                    }
                    out.println("最近一次更新" + Dates.YYYY_MM_DD.format(doc.getLastModifiedDate()) //
                            + "，打印时间" + Dates.YYYY_MM_DD.format(new Date()) + ".");
                    break;

                default:
                    throw new IllegalUsageException("Unknown variable.");
                }
                return out;
            }

        });
        String tex = expander.process(texTemplate);
        out.println(tex);
    }

}
