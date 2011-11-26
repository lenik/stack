package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 科目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_subject_seq", allocationSize = 1)
@AttributeOverride(name = "label", column = @Column(unique = true))
public class AccountSubject
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 10;

    public static final int DEBIT = 1;
    public static final int CREDIT = 2;
    public static final int BOTH = DEBIT | CREDIT;

    boolean debitSign;
    boolean creditSign;

    public AccountSubject() {
        super();
    }

    public AccountSubject(String name, String label) {
        super(name, label);
    }

    public AccountSubject(String name, String label, String description) {
        super(name, label, description);
    }

    public AccountSubject(String name, String label, int flags) {
        super(name, label);
        this.debitSign = (flags & DEBIT) != 0;
        this.creditSign = (flags & CREDIT) != 0;
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
     */
    @Column(nullable = false)
    public boolean isCreditSign() {
        return creditSign;
    }

    public void setCreditSign(boolean creditSign) {
        this.creditSign = creditSign;
    }

    private static AccountSubject _(String name, String label, int flags) {
        return new AccountSubject(name, label, flags);
    }

    //资产类
    public static final AccountSubject s1001 = new AccountSubject("1001","现金",true,false);
    public static final AccountSubject s1002 = new AccountSubject("1002","银行存款",true,false);
    public static final AccountSubject s1009 = new AccountSubject("1009","其他货币资金",true,false);
    public static final AccountSubject s100901 = new AccountSubject("100901","外埠存款",true,false);
    public static final AccountSubject s100902 = new AccountSubject("100902","银行本票",true,false);
    public static final AccountSubject s100903 = new AccountSubject("100903","银行汇票",true,false);
    public static final AccountSubject s100904 = new AccountSubject("100904","信用卡",true,false);
    public static final AccountSubject s100905 = new AccountSubject("100905","信用证保证金",true,false);
    public static final AccountSubject s100906 = new AccountSubject("100906","存出投资款",true,false);
    public static final AccountSubject s1101 = new AccountSubject("1101","短期投资",true,false);
    public static final AccountSubject s110101 = new AccountSubject("110101","股票",true,false);
    public static final AccountSubject s110102 = new AccountSubject("110102","债券",true,false);
    public static final AccountSubject s110103 = new AccountSubject("110103","基金",true,false);
    public static final AccountSubject s110110 = new AccountSubject("110110","其他",true,false);
    public static final AccountSubject s1102 = new AccountSubject("1102","短期投资跌价准备",true,false);
    public static final AccountSubject s1111 = new AccountSubject("1111","应收票据",true,false);
    public static final AccountSubject s1121 = new AccountSubject("1121","应收股利",true,false);
    public static final AccountSubject s1122 = new AccountSubject("1122","应收利息",true,false);
    public static final AccountSubject s1131 = new AccountSubject("1131","应收账款",true,false);
    public static final AccountSubject s1133 = new AccountSubject("1133","其他应收款",true,false);
    public static final AccountSubject s1141 = new AccountSubject("1141","坏账准备",false,true);
    public static final AccountSubject s1151 = new AccountSubject("1151","预付账款",true,false);
    public static final AccountSubject s1161 = new AccountSubject("1161","应收补贴款",true,false);
    public static final AccountSubject s1201 = new AccountSubject("1201","物资采购",true,false);
    public static final AccountSubject s1211 = new AccountSubject("1211","原材料",true,false);
    public static final AccountSubject s1221 = new AccountSubject("1221","包装物",true,false);
    public static final AccountSubject s1231 = new AccountSubject("1231","低值易耗品",true,false);
    public static final AccountSubject s1232 = new AccountSubject("1232","材料成本差异",true,false);
    public static final AccountSubject s1241 = new AccountSubject("1241","自制半成品",true,false);
    public static final AccountSubject s1243 = new AccountSubject("1243","库存商品",true,false);
    public static final AccountSubject s1244 = new AccountSubject("1244","商品进销差价",true,false);
    public static final AccountSubject s1251 = new AccountSubject("1251","委托加工物资",true,false);
    public static final AccountSubject s1261 = new AccountSubject("1261","委托代销商品",true,false);
    public static final AccountSubject s1271 = new AccountSubject("1271","受托代销商品",true,false);
    public static final AccountSubject s1281 = new AccountSubject("1281","存货跌价准备",true,false);
    public static final AccountSubject s1291 = new AccountSubject("1291","分期收款发出商品",true,false);
    public static final AccountSubject s1301 = new AccountSubject("1301","待摊费用",true,false);
    public static final AccountSubject s1401 = new AccountSubject("1401","长期股权投资",true,false);
    public static final AccountSubject s140101 = new AccountSubject("140101","股票投资",true,false);
    public static final AccountSubject s140102 = new AccountSubject("140102","其他股权投资",true,false);
    public static final AccountSubject s1402 = new AccountSubject("1402","长期债权投资",true,false);
    public static final AccountSubject s140201 = new AccountSubject("140201","债券投资",true,false);
    public static final AccountSubject s140202 = new AccountSubject("140202","其他债权投资",true,false);
    public static final AccountSubject s1421 = new AccountSubject("1421","长期投资减值准备",true,false);
    public static final AccountSubject s1431 = new AccountSubject("1431","委托贷款",true,false);
    public static final AccountSubject s143101 = new AccountSubject("143101","本金",true,false);
    public static final AccountSubject s143102 = new AccountSubject("143102","利息",true,false);
    public static final AccountSubject s143103 = new AccountSubject("143103","减值准备",true,false);
    public static final AccountSubject s1501 = new AccountSubject("1501","固定资产",true,false);
    public static final AccountSubject s1502 = new AccountSubject("1502","累计折旧",false,true);
    public static final AccountSubject s1505 = new AccountSubject("1505","固定资产减值准备",true,false);
    public static final AccountSubject s1601 = new AccountSubject("1601","工程物资",true,false);
    public static final AccountSubject s160101 = new AccountSubject("160101","专用材料",true,false);
    public static final AccountSubject s160102 = new AccountSubject("160102","专用设备",true,false);
    public static final AccountSubject s160103 = new AccountSubject("160103","预付大型设备款",true,false);
    public static final AccountSubject s160104 = new AccountSubject("160104","为生产准备的工具及器具",true,false);
    public static final AccountSubject s1603 = new AccountSubject("1603","在建工程",true,false);
    public static final AccountSubject s1605 = new AccountSubject("1605","在建工程减值准备",true,false);
    public static final AccountSubject s1701 = new AccountSubject("1701","固定资产清理",true,false);
    public static final AccountSubject s1801 = new AccountSubject("1801","无形资产",true,false);
    public static final AccountSubject s1805 = new AccountSubject("1805","无形资产减值准备",true,false);
    public static final AccountSubject s1815 = new AccountSubject("1815","未确认融资费用",true,false);
    public static final AccountSubject s1901 = new AccountSubject("1901","长期待摊费用",true,false);
    public static final AccountSubject s1911 = new AccountSubject("1911","待处理财产损溢",true,false);
    public static final AccountSubject s191101 = new AccountSubject("191101","待处理流动资产损溢",true,false);
    public static final AccountSubject s191102 = new AccountSubject("191102","待处理固定资产损溢",true,false);

    //负债类
    public static final AccountSubject s2101 = new AccountSubject("2101","短期借款",false,true);
    public static final AccountSubject s2111 = new AccountSubject("2111","应付票据",false,true);
    public static final AccountSubject s2121 = new AccountSubject("2121","应付账款",false,true);
    public static final AccountSubject s2131 = new AccountSubject("2131","预收账款",false,true);
    public static final AccountSubject s2141 = new AccountSubject("2141","代销商品款",false,true);
    public static final AccountSubject s2151 = new AccountSubject("2151","应付工资",false,true);
    public static final AccountSubject s2153 = new AccountSubject("2153","应付福利费",false,true);
    public static final AccountSubject s2161 = new AccountSubject("2161","应付股利",false,true);
    public static final AccountSubject s2171 = new AccountSubject("2171","应交税金",false,true);
    public static final AccountSubject s217101 = new AccountSubject("217101","应交增值税",false,true);
    public static final AccountSubject s21710101 = new AccountSubject("21710101","进项税额",false,true);
    public static final AccountSubject s21710102 = new AccountSubject("21710102","已交税金",false,true);
    public static final AccountSubject s21710103 = new AccountSubject("21710103","转出未交增值税",false,true);
    public static final AccountSubject s21710104 = new AccountSubject("21710104","减免税款",false,true);
    public static final AccountSubject s21710105 = new AccountSubject("21710105","销项税额",false,true);
    public static final AccountSubject s21710106 = new AccountSubject("21710106","出口退税",false,true);
    public static final AccountSubject s21710107 = new AccountSubject("21710107","进项税额转出",false,true);
    public static final AccountSubject s21710108 = new AccountSubject("21710108","出口抵减内销产品应纳税额",false,true);
    public static final AccountSubject s21710109 = new AccountSubject("21710109","转出多交增值税",false,true);
    public static final AccountSubject s21710110 = new AccountSubject("21710110","未交增值税",false,true);
    public static final AccountSubject s217102 = new AccountSubject("217102","应交营业税",false,true);
    public static final AccountSubject s217103 = new AccountSubject("217103","应交消费税",false,true);
    public static final AccountSubject s217104 = new AccountSubject("217104","应交资源税",false,true);
    public static final AccountSubject s217105 = new AccountSubject("217105","应交所得税",false,true);
    public static final AccountSubject s217106 = new AccountSubject("217106","应交土地增值税",false,true);
    public static final AccountSubject s217107 = new AccountSubject("217107","应交城市维护建设税",false,true);
    public static final AccountSubject s217108 = new AccountSubject("217108","应交房产税",false,true);
    public static final AccountSubject s217109 = new AccountSubject("217109","应交土地使用税",false,true);
    public static final AccountSubject s217110 = new AccountSubject("217110","应交车船使用税",false,true);
    public static final AccountSubject s217111 = new AccountSubject("217111","应交个人所得税",false,true);
    public static final AccountSubject s2176 = new AccountSubject("2176","其他应交款",false,true);
    public static final AccountSubject s2181 = new AccountSubject("2181","其他应付款",false,true);
    public static final AccountSubject s2191 = new AccountSubject("2191","预提费用",false,true);
    public static final AccountSubject s2201 = new AccountSubject("2201","待转资产价值",false,true);
    public static final AccountSubject s2211 = new AccountSubject("2211","预计负债",false,true);
    public static final AccountSubject s2301 = new AccountSubject("2301","长期借款",false,true);
    public static final AccountSubject s2311 = new AccountSubject("2311","应付债券",false,true);
    public static final AccountSubject s231101 = new AccountSubject("231101","债券面值",false,true);
    public static final AccountSubject s231102 = new AccountSubject("231102","债券溢价",false,true);
    public static final AccountSubject s231103 = new AccountSubject("231103","债券折价",false,true);
    public static final AccountSubject s231104 = new AccountSubject("231104","应计利息",false,true);
    public static final AccountSubject s2321 = new AccountSubject("2321","长期应付款",false,true);
    public static final AccountSubject s2331 = new AccountSubject("2331","专项应付款",false,true);
    public static final AccountSubject s2341 = new AccountSubject("2341","递延税款",false,true);

    //所有者权益类
    public static final AccountSubject s3101 = new AccountSubject("3101","实收资本(或股本)",false,true);
    public static final AccountSubject s3103 = new AccountSubject("3103","已归还投资",false,true);
    public static final AccountSubject s3111 = new AccountSubject("3111","资本公积",false,true);
    public static final AccountSubject s311101 = new AccountSubject("311101","资本(或股本)溢价",false,true);
    public static final AccountSubject s311102 = new AccountSubject("311102","接受捐赠非现金资产准备",false,true);
    public static final AccountSubject s311103 = new AccountSubject("311103","接受现金捐赠",false,true);
    public static final AccountSubject s311104 = new AccountSubject("311104","股权投资准备",false,true);
    public static final AccountSubject s311105 = new AccountSubject("311105","拨款转入",false,true);
    public static final AccountSubject s311106 = new AccountSubject("311106","外币资本折算差额",false,true);
    public static final AccountSubject s311107 = new AccountSubject("311107","其他资本公积",false,true);
    public static final AccountSubject s3121 = new AccountSubject("3121","盈余公积",false,true);
    public static final AccountSubject s312101 = new AccountSubject("312101","法定盈余公积",false,true);
    public static final AccountSubject s312102 = new AccountSubject("312102","任意盈余公积",false,true);
    public static final AccountSubject s312103 = new AccountSubject("312103","法定公益金",false,true);
    public static final AccountSubject s312104 = new AccountSubject("312104","储备基金",false,true);
    public static final AccountSubject s312105 = new AccountSubject("312105","企业发展基金",false,true);
    public static final AccountSubject s312106 = new AccountSubject("312106","利润归还投资",false,true);
    public static final AccountSubject s3131 = new AccountSubject("3131","本年利润",false,true);
    public static final AccountSubject s3141 = new AccountSubject("3141","利润分配",false,true);
    public static final AccountSubject s314101 = new AccountSubject("314101","其他转入",false,true);
    public static final AccountSubject s314102 = new AccountSubject("314102","提取法定盈余公积",false,true);
    public static final AccountSubject s314103 = new AccountSubject("314103","提取法定公益金",false,true);
    public static final AccountSubject s314104 = new AccountSubject("314104","提取储备基金",false,true);
    public static final AccountSubject s314105 = new AccountSubject("314105","提取企业发展基金",false,true);
    public static final AccountSubject s314106 = new AccountSubject("314106","提取职工奖励及福利基金",false,true);
    public static final AccountSubject s314107 = new AccountSubject("314107","利润归还投资",false,true);
    public static final AccountSubject s314108 = new AccountSubject("314108","应付优先股股利",false,true);
    public static final AccountSubject s314109 = new AccountSubject("314109","提取任意盈余公积",false,true);
    public static final AccountSubject s314110 = new AccountSubject("314110","应付普通股股利",false,true);
    public static final AccountSubject s314111 = new AccountSubject("314111","转作资本(或股本)的普通股股利",false,true);
    public static final AccountSubject s314115 = new AccountSubject("314115","未分配利润",false,true);

    //成本类
    public static final AccountSubject s4101 = new AccountSubject("4101","生产成本",true,false);
    public static final AccountSubject s410101 = new AccountSubject("410101","基本生产成本",true,false);
    public static final AccountSubject s410102 = new AccountSubject("410102","辅助生产成本",true,false);
    public static final AccountSubject s4105 = new AccountSubject("4105","制造费用",true,false);
    public static final AccountSubject s4107 = new AccountSubject("4107","劳务成本",true,false);

    //损益类
    public static final AccountSubject s5101 = new AccountSubject("5101","主营业务收入",false,true);
    public static final AccountSubject s5102 = new AccountSubject("5102","其他业务收入",false,true);
    public static final AccountSubject s5201 = new AccountSubject("5201","投资收益",false,true);
    public static final AccountSubject s5203 = new AccountSubject("5203","补贴收入",false,true);
    public static final AccountSubject s5301 = new AccountSubject("5301","营业外收入",false,true);
    public static final AccountSubject s5401 = new AccountSubject("5401","主营业务成本",true,false);
    public static final AccountSubject s5402 = new AccountSubject("5402","主营业务税金及附加",true,false);
    public static final AccountSubject s5405 = new AccountSubject("5405","其他业务支出",true,false);
    public static final AccountSubject s5501 = new AccountSubject("5501","营业费用",true,false);
    public static final AccountSubject s5502 = new AccountSubject("5502","管理费用",true,false);
    public static final AccountSubject s5503 = new AccountSubject("5503","财务费用",true,false);
    public static final AccountSubject s5601 = new AccountSubject("5601","营业外支出",true,false);
    public static final AccountSubject s5701 = new AccountSubject("5701","所得税",true,false);
    public static final AccountSubject s5801 = new AccountSubject("5801","以前年度损益调整",true,false);

}
