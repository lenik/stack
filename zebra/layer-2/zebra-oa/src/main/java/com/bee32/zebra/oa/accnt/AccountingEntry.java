package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;

import javax.persistence.Table;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.repr.form.meta.FormInput;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.model.base.CoMomentInterval;
import net.bodz.lily.model.base.IdType;

import com.bee32.zebra.oa.OaGroups;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;

/**
 * 会计分录
 */
@IdType(Long.class)
@Table(name = "acentry")
public class AccountingEntry
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    private AccountingEvent event;
    private Account account;
    private Organization org;
    private Person person;

    private BigDecimal value = BigDecimal.ZERO;
    private DebitOrCredit side = DebitOrCredit.DEBIT;

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_PRIVATE);
    }

    /**
     * 凭证单
     */
    @FormInput(readOnly = true)
    public AccountingEvent getEvent() {
        return event;
    }

    public void setEvent(AccountingEvent event) {
        this.event = event;
    }

    /**
     * 会计科目是按照经济业务的内容和经济管理的要求，对会计要素的具体内容进行分类核算的科目。 会计科目按其所提供信息的详细程度及其统驭关系不同，又分为总分类科目和明细分类科目。
     * 
     * 前者是对会计要素具体内容进行总括分类 ，提供总括信息的会计科目，如“应收账款”、“原材料”等科目，
     * 
     * 后者是对总分类科目作进一步分类 、提供更详细更具体会计信息科目，如“应收账款”科目按债务人名称设置明细科目，反映应收账款具体对象。
     * 
     * @label 科目
     */
    @OfGroup(StdGroup.Classification.class)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 公司
     * 
     * 凭证记账下的功能相当于会计科目。
     */
    @OfGroup(StdGroup.Classification.class)
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     * 
     * 凭证记账下的功能相当于会计科目。
     */
    @OfGroup(StdGroup.Classification.class)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 金额
     */
    @DetailLevel(DetailLevel.HIDDEN)
    @OfGroup(OaGroups.Accounting.class)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;

        if (value.signum() >= 0)
            side = DebitOrCredit.DEBIT;
        else
            side = DebitOrCredit.CREDIT;
    }

    /**
     * 借方：显示资产方的增加或负债方的减少；对应概念为贷方。
     * <ol>
     * <li>对于资产类、费用类账户（如现金、银行存款、材料、固定资产、应收款、管理费用、主营业务成本等），“借”就是加；
     * <li>对于负债、所有者权益、收入类账户（如应付款、长/短期借款、主营业务收入、实收资本、本年利润等），“借”就是减。
     * </ol>
     * 
     * 贷方：显示资产方的减少或负债方的增加；对应概念为借方。
     * <ol>
     * <li>对于负债、所有者权益、收入类账户（如应付款、长/短期借款、主营业务收入、实收资本、本年利润等），“贷”就是加。
     * <li>对于资产类、费用类账户（如现金、银行存款、材料、固定资产、应收款、管理费用、主营业务成本等），“贷”就是减；
     * </ol>
     * 
     * @label 借贷方
     */
    @OfGroup(OaGroups.Accounting.class)
    public DebitOrCredit getSide() {
        return side;
    }

    public void setSide(DebitOrCredit side) {
        this.side = side;
        if (side == DebitOrCredit.DEBIT) {
            if (value.signum() == -1)
                value = value.negate();
        } else {
            if (value.signum() == 1)
                value = value.negate();
        }
    }

    /**
     * 金额
     */
    @OfGroup(OaGroups.Accounting.class)
    public BigDecimal getAbsValue() {
        return value.abs();
    }

    public void setAbsValue(BigDecimal absValue) {
        if (side == DebitOrCredit.DEBIT)
            value = absValue;
        else
            value = absValue.negate();
    }

}
