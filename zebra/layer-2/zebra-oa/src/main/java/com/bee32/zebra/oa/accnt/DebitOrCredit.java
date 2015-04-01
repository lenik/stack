package com.bee32.zebra.oa.accnt;

import net.bodz.bas.html.meta.ViewCriteria;
import net.bodz.bas.t.predef.Predef;
import net.bodz.bas.t.predef.PredefMetadata;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocAware;

@ViewCriteria("radio")
public class DebitOrCredit
        extends Predef<DebitOrCredit, Character> {

    private static final long serialVersionUID = 1L;

    public static final PredefMetadata<DebitOrCredit, Character> METADATA = PredefMetadata
            .forClass(DebitOrCredit.class);

    private DebitOrCredit(char val, String name) {
        super(val, name, METADATA);
    }

    /**
     * 借方
     * 
     * 显示资产方的增加或负债方的减少；对应概念为贷方。
     * <ol>
     * <li>对于资产类、费用类账户（如现金、银行存款、材料、固定资产、应收款、管理费用、主营业务成本等），“借”就是加；
     * <li>对于负债、所有者权益、收入类账户（如应付款、长/短期借款、主营业务收入、实收资本、本年利润等），“借”就是减。
     * </ol>
     */
    public static final DebitOrCredit DEBIT = new DebitOrCredit('d', "DEBIT");

    /**
     * 贷方
     * 
     * 显示资产方的减少或负债方的增加；对应概念为借方。
     * <ol>
     * <li>对于负债、所有者权益、收入类账户（如应付款、长/短期借款、主营业务收入、实收资本、本年利润等），“贷”就是加。
     * <li>对于资产类、费用类账户（如现金、银行存款、材料、固定资产、应收款、管理费用、主营业务成本等），“贷”就是减；
     * </ol>
     */
    public static final DebitOrCredit CREDIT = new DebitOrCredit('c', "CREDIT");

    static {
        IXjdocAware.fn.injectFields(DebitOrCredit.class, false);
    }

}
