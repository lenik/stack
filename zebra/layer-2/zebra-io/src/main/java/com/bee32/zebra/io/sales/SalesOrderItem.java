package com.bee32.zebra.io.sales;

import java.util.Date;

import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.repr.form.meta.TextInput;

import com.bee32.zebra.io.art.Artifact;
import com.tinylily.model.base.CoMomentInterval;

@TableName("sentry")
public class SalesOrderItem
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    public static final int N_ALT_LABEL = 30;
    public static final int N_ALT_SPEC = 30;
    public static final int N_COMMENT1 = 200;
    public static final int N_FOOTNOTE = 200;

    SalesOrder order;
    Artifact artifact;

    String altLabel;
    String altSpec;
    String altUom;

    double quantity;
    double price;
    String footnote;

    public SalesOrder getOrder() {
        return order;
    }

    public void setOrder(SalesOrder order) {
        this.order = order;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    /**
     * 订单时间
     */
    public Date getOrderTime() {
        return super.getBeginDate();
    }

    /**
     * 交货时间
     */
    public Date getDeadline() {
        return super.getEndDate();
    }

    /**
     * 铭牌名称
     */
    @TextInput(maxLength = N_ALT_LABEL)
    public String getAltLabel() {
        return altLabel;
    }

    public void setAltLabel(String altLabel) {
        this.altLabel = altLabel;
    }

    /**
     * 铭牌规格
     */
    @TextInput(maxLength = N_ALT_SPEC)
    public String getAltSpec() {
        return altSpec;
    }

    public void setAltSpec(String altSpec) {
        this.altSpec = altSpec;
    }

    /**
     * 数量
     */
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 价格
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 附注
     */
    @TextInput(maxLength = N_FOOTNOTE)
    public String getFootnote() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

}