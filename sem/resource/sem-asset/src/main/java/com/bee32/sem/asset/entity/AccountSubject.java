package com.bee32.sem.asset.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 科目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "asset_subject_seq", allocationSize = 1)
@AttributeOverride(name = "label", column = @Column(unique = true))
public class AccountSubject
        extends TreeEntityAuto<Integer, AccountSubject> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 10;

    boolean debitSign;
    boolean creditSign;

    public AccountSubject() {
        super();
    }

    public AccountSubject(String name) {
        super(name);
    }

    public AccountSubject(AccountSubject parent) {
        super(parent, null);
    }

    public AccountSubject(AccountSubject parent, String name) {
        super(parent, name);
    }

    public AccountSubject(String name, String label, boolean debitSign, boolean creditSign) {
        this.name = name;
        this.label = label;
        this.debitSign = debitSign;
        this.creditSign = creditSign;
    }

    /**
     * 科目代码
     */
    @NaturalId(mutable = true)
    @Column(length = NAME_LENGTH, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 借方符号
     */
    @Column(nullable = false)
    public boolean isDebitSign() {
        return debitSign;
    }

    public void setDebitSign(boolean debitSign) {
        this.debitSign = debitSign;
    }

    /**
     * 贷方符号
     * @return
     */
    @Column(nullable = false)
    public boolean isCreditSign() {
        return creditSign;
    }

    public void setCreditSign(boolean creditSign) {
        this.creditSign = creditSign;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }


    //资产类
    public static final AccountSubject s1001 = new AccountSubject("1001","现金",true,false);
    public static final AccountSubject s1002 = new AccountSubject("1002","银行存款",true,false);
    public static final AccountSubject s1003 = new AccountSubject("1003","存放中央银行款项",true,false);
    public static final AccountSubject s1011 = new AccountSubject("1011","存放同业",true,false);
    public static final AccountSubject s1015 = new AccountSubject("1015","其他货币资金",true,false);
    public static final AccountSubject s1021 = new AccountSubject("1021","结算备付金",true,false);
    public static final AccountSubject s1031 = new AccountSubject("1031","存出保证金",true,false);
    public static final AccountSubject s1051 = new AccountSubject("1051","拆出资金",true,false);
    public static final AccountSubject s1101 = new AccountSubject("1101","交易性金融资产",true,false);
    public static final AccountSubject s1111 = new AccountSubject("1111","买入返售金融资产",true,false);
    public static final AccountSubject s1121 = new AccountSubject("1121","应收票据",true,false);
    public static final AccountSubject s1122 = new AccountSubject("1122","应收账款",true,false);
    public static final AccountSubject s1123 = new AccountSubject("1123","预付账款",true,false);
    public static final AccountSubject s1131 = new AccountSubject("1131","应收股利",true,false);
    public static final AccountSubject s1132 = new AccountSubject("1132","应收利息",true,false);
    public static final AccountSubject s1211 = new AccountSubject("1211","应收保户储金",true,false);
    public static final AccountSubject s1221 = new AccountSubject("1221","应收代位追偿款",true,false);
    public static final AccountSubject s1222 = new AccountSubject("1222","应收分保账款",true,false);
    public static final AccountSubject s1223 = new AccountSubject("1223","应收分保未到期责任准备金",true,false);
    public static final AccountSubject s1224 = new AccountSubject("1224","应收分保保险责任准备金",true,false);
    public static final AccountSubject s1231 = new AccountSubject("1231","其他应收款",true,false);
    public static final AccountSubject s1241 = new AccountSubject("1241","坏账准备",true,false);
    public static final AccountSubject s1251 = new AccountSubject("1251","贴现资产",true,false);
    public static final AccountSubject s1301 = new AccountSubject("1301","贷款",true,false);
    public static final AccountSubject s1302 = new AccountSubject("1302","贷款损失准备",true,false);
    public static final AccountSubject s1311 = new AccountSubject("1311","代理兑付证券",true,false);
    public static final AccountSubject s1321 = new AccountSubject("1321","代理业务资产",true,false);
    public static final AccountSubject s1401 = new AccountSubject("1401","材料采购",true,false);
    public static final AccountSubject s1402 = new AccountSubject("1402","在途物资",true,false);
    public static final AccountSubject s1403 = new AccountSubject("1403","原材料",true,false);
    public static final AccountSubject s1404 = new AccountSubject("1404","材料成本差异",true,false);
    public static final AccountSubject s1406 = new AccountSubject("1406","库存商品",true,false);
    public static final AccountSubject s1407 = new AccountSubject("1407","发出商品",true,false);
    public static final AccountSubject s1410 = new AccountSubject("1410","商品进销差价",true,false);
    public static final AccountSubject s1411 = new AccountSubject("1411","委托加工物资",true,false);
    public static final AccountSubject s1412 = new AccountSubject("1412","包装物及低值易耗品",true,false);
    public static final AccountSubject s1421 = new AccountSubject("1421","消耗性生物资产",true,false);
    public static final AccountSubject s1431 = new AccountSubject("1431","周转材料",true,false);
    public static final AccountSubject s1441 = new AccountSubject("1441","贵金属",true,false);
    public static final AccountSubject s1442 = new AccountSubject("1442","抵债资产",true,false);
    public static final AccountSubject s1451 = new AccountSubject("1451","损余物资",true,false);
    public static final AccountSubject s1461 = new AccountSubject("1461","存货跌价准备",true,false);
    public static final AccountSubject s1501 = new AccountSubject("1501","待摊费用",true,false);
    public static final AccountSubject s1511 = new AccountSubject("1511","独立账户资产",true,false);
    public static final AccountSubject s1521 = new AccountSubject("1521","持有至到期投资",true,false);
    public static final AccountSubject s1522 = new AccountSubject("1522","持有至到期投资减值准备",true,false);
    public static final AccountSubject s1523 = new AccountSubject("1523","可供出售金融资产",true,false);
    public static final AccountSubject s1524 = new AccountSubject("1524","长期股权投资",true,false);
    public static final AccountSubject s1525 = new AccountSubject("1525","长期股权投资减值准备",true,false);
    public static final AccountSubject s1526 = new AccountSubject("1526","投资性房地产",true,false);
    public static final AccountSubject s1531 = new AccountSubject("1531","长期应收款",true,false);
    public static final AccountSubject s1541 = new AccountSubject("1541","未实现融资收益",true,false);
    public static final AccountSubject s1551 = new AccountSubject("1551","存出资本保证金",true,false);
    public static final AccountSubject s1601 = new AccountSubject("1601","固定资产",true,false);
    public static final AccountSubject s1602 = new AccountSubject("1602","累计折旧",true,false);
    public static final AccountSubject s1603 = new AccountSubject("1603","固定资产减值准备",true,false);
    public static final AccountSubject s1604 = new AccountSubject("1604","在建工程",true,false);
    public static final AccountSubject s1605 = new AccountSubject("1605","工程物资",true,false);
    public static final AccountSubject s1606 = new AccountSubject("1606","固定资产清理",true,false);
    public static final AccountSubject s1611 = new AccountSubject("1611","融资租赁资产",true,false);
    public static final AccountSubject s1612 = new AccountSubject("1612","未担保余值",true,false);
    public static final AccountSubject s1621 = new AccountSubject("1621","生产性生物资产",true,false);
    public static final AccountSubject s1622 = new AccountSubject("1622","生产性生物资产累计折旧",true,false);
    public static final AccountSubject s1623 = new AccountSubject("1623","公益性生物资产",true,false);
    public static final AccountSubject s1631 = new AccountSubject("1631","油气资产",true,false);
    public static final AccountSubject s1632 = new AccountSubject("1632","累计折耗",true,false);
    public static final AccountSubject s1701 = new AccountSubject("1701","无形资产",true,false);
    public static final AccountSubject s1702 = new AccountSubject("1702","累计摊销",true,false);
    public static final AccountSubject s1703 = new AccountSubject("1703","无形资产减值准备",true,false);
    public static final AccountSubject s1711 = new AccountSubject("1711","商誉",true,false);
    public static final AccountSubject s1801 = new AccountSubject("1801","长期待摊费用",true,false);
    public static final AccountSubject s1811 = new AccountSubject("1811","递延所得税资产",true,false);
    public static final AccountSubject s1901 = new AccountSubject("1901","待处理财产损溢",true,false);

    //负债类
    public static final AccountSubject s2001 = new AccountSubject("2001","短期借款",true,false);
    public static final AccountSubject s2002 = new AccountSubject("2002","存入保证金",true,false);
    public static final AccountSubject s2003 = new AccountSubject("2003","拆入资金",true,false);
    public static final AccountSubject s2004 = new AccountSubject("2004","向中央银行借款",true,false);
    public static final AccountSubject s2011 = new AccountSubject("2011","同业存放",true,false);
    public static final AccountSubject s2012 = new AccountSubject("2012","吸收存款",true,false);
    public static final AccountSubject s2021 = new AccountSubject("2021","贴现负债",true,false);
    public static final AccountSubject s2101 = new AccountSubject("2101","交易性金融负债",true,false);
    public static final AccountSubject s2111 = new AccountSubject("2111","卖出回购金融资产款",true,false);
    public static final AccountSubject s2201 = new AccountSubject("2201","应付票据",true,false);
    public static final AccountSubject s2202 = new AccountSubject("2202","应付账款",false,true);
    public static final AccountSubject s2205 = new AccountSubject("2205","预收账款",true,false);
    public static final AccountSubject s2211 = new AccountSubject("2211","应付职工薪酬",true,false);
    public static final AccountSubject s2221 = new AccountSubject("2221","应交税费",true,false);
    public static final AccountSubject s2231 = new AccountSubject("2231","应付股利",true,false);
    public static final AccountSubject s2232 = new AccountSubject("2232","应付利息",true,false);
    public static final AccountSubject s2241 = new AccountSubject("2241","其他应付款",true,false);
    public static final AccountSubject s2251 = new AccountSubject("2251","应付保户红利",true,false);
    public static final AccountSubject s2261 = new AccountSubject("2261","应付分保账款",true,false);
    public static final AccountSubject s2311 = new AccountSubject("2311","代理买卖证券款",true,false);
    public static final AccountSubject s2312 = new AccountSubject("2312","代理承销证券款",true,false);
    public static final AccountSubject s2313 = new AccountSubject("2313","代理兑付证券款",true,false);
    public static final AccountSubject s2314 = new AccountSubject("2314","代理业务负债",true,false);
    public static final AccountSubject s2401 = new AccountSubject("2401","预提费用",true,false);
    public static final AccountSubject s2411 = new AccountSubject("2411","预计负债",true,false);
    public static final AccountSubject s2501 = new AccountSubject("2501","递延收益",true,false);
    public static final AccountSubject s2601 = new AccountSubject("2601","长期借款",true,false);
    public static final AccountSubject s2602 = new AccountSubject("2602","长期债券",true,false);
    public static final AccountSubject s2701 = new AccountSubject("2701","未到期责任准备金",true,false);
    public static final AccountSubject s2702 = new AccountSubject("2702","保险责任准备金",true,false);
    public static final AccountSubject s2711 = new AccountSubject("2711","保户储金",true,false);
    public static final AccountSubject s2721 = new AccountSubject("2721","独立账户负债",true,false);
    public static final AccountSubject s2801 = new AccountSubject("2801","长期应付款",true,false);
    public static final AccountSubject s2802 = new AccountSubject("2802","未确认融资费用",true,false);
    public static final AccountSubject s2811 = new AccountSubject("2811","专项应付款",true,false);
    public static final AccountSubject s2901 = new AccountSubject("2901","递延所得税负债",true,false);

    //共同类
    public static final AccountSubject s3001 = new AccountSubject("3001","清算资金往来",true,false);
    public static final AccountSubject s3002 = new AccountSubject("3002","外汇买卖",true,false);
    public static final AccountSubject s3101 = new AccountSubject("3101","衍生工具",true,false);
    public static final AccountSubject s3201 = new AccountSubject("3201","套期工具",true,false);
    public static final AccountSubject s3202 = new AccountSubject("3202","被套期项目",true,false);

    //所有者权益类
    public static final AccountSubject s4001 = new AccountSubject("4001","实收资本",true,false);
    public static final AccountSubject s4002 = new AccountSubject("4002","资本公积",true,false);
    public static final AccountSubject s4101 = new AccountSubject("4101","盈余公积",true,false);
    public static final AccountSubject s4102 = new AccountSubject("4102","一般风险准备",true,false);
    public static final AccountSubject s4103 = new AccountSubject("4103","本年利润",true,false);
    public static final AccountSubject s4104 = new AccountSubject("4104","利润分配",true,false);
    public static final AccountSubject s4201 = new AccountSubject("4201","库存股",true,false);

    //成本类
    public static final AccountSubject s5001 = new AccountSubject("5001","生产成本",true,false);
    public static final AccountSubject s5101 = new AccountSubject("5101","制造费用",true,false);
    public static final AccountSubject s5201 = new AccountSubject("5201","劳务成本",true,false);
    public static final AccountSubject s5301 = new AccountSubject("5301","研发支出",true,false);
    public static final AccountSubject s5401 = new AccountSubject("5401","工程施工",true,false);
    public static final AccountSubject s5402 = new AccountSubject("5402","工程结算",true,false);
    public static final AccountSubject s5403 = new AccountSubject("5403","机械作业",true,false);

    //损益类
    public static final AccountSubject s6001 = new AccountSubject("6001","主营业务收入",true,false);
    public static final AccountSubject s6011 = new AccountSubject("6011","利息收入",true,false);
    public static final AccountSubject s6021 = new AccountSubject("6021","手续费收入",true,false);
    public static final AccountSubject s6031 = new AccountSubject("6031","保费收入",true,false);
    public static final AccountSubject s6032 = new AccountSubject("6032","分保费收入",true,false);
    public static final AccountSubject s6041 = new AccountSubject("6041","租赁收入",true,false);
    public static final AccountSubject s6051 = new AccountSubject("6051","其他业务收入",true,false);
    public static final AccountSubject s6061 = new AccountSubject("6061","汇兑损益",true,false);
    public static final AccountSubject s6101 = new AccountSubject("6101","公允价值变动损益",true,false);
    public static final AccountSubject s6111 = new AccountSubject("6111","投资收益",true,false);
    public static final AccountSubject s6201 = new AccountSubject("6201","摊回保险责任准备金",true,false);
    public static final AccountSubject s6202 = new AccountSubject("6202","摊回赔付支出",true,false);
    public static final AccountSubject s6203 = new AccountSubject("6203","摊回分保费用",true,false);
    public static final AccountSubject s6301 = new AccountSubject("6301","营业外收入",true,false);
    public static final AccountSubject s6401 = new AccountSubject("6401","主营业务成本",true,false);
    public static final AccountSubject s6402 = new AccountSubject("6402","其他业务支出",true,false);
    public static final AccountSubject s6405 = new AccountSubject("6405","营业税金及附加",true,false);
    public static final AccountSubject s6411 = new AccountSubject("6411","利息支出",true,false);
    public static final AccountSubject s6421 = new AccountSubject("6421","手续费支出",true,false);
    public static final AccountSubject s6501 = new AccountSubject("6501","提取未到期责任准备金",true,false);
    public static final AccountSubject s6502 = new AccountSubject("6502","提取保险责任准备金",true,false);
    public static final AccountSubject s6511 = new AccountSubject("6511","赔付支出",true,false);
    public static final AccountSubject s6521 = new AccountSubject("6521","保户红利支出",true,false);
    public static final AccountSubject s6531 = new AccountSubject("6531","退保金",true,false);
    public static final AccountSubject s6541 = new AccountSubject("6541","分出保费",true,false);
    public static final AccountSubject s6542 = new AccountSubject("6542","分保费用",true,false);
    public static final AccountSubject s6601 = new AccountSubject("6601","销售费用",true,false);
    public static final AccountSubject s6602 = new AccountSubject("6602","管理费用",true,false);
    public static final AccountSubject s6603 = new AccountSubject("6603","财务费用",true,false);
    public static final AccountSubject s6604 = new AccountSubject("6604","勘探费用",true,false);
    public static final AccountSubject s6701 = new AccountSubject("6701","资产减值损失",true,false);
    public static final AccountSubject s6711 = new AccountSubject("6711","营业外支出",true,false);
    public static final AccountSubject s6801 = new AccountSubject("6801","所得税",true,false);
    public static final AccountSubject s6901 = new AccountSubject("6901","以前年度损益调整",true,false);
}
