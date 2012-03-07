package com.bee32.sem.asset.entity;

import static com.bee32.sem.asset.entity.AccountSubject.CREDIT;
import static com.bee32.sem.asset.entity.AccountSubject.DEBIT;

import com.bee32.plover.orm.sample.StandardSamples;

public class AccountSubjects
        extends StandardSamples {

    static AccountSubject _(String name, String label, int flags) {
        return new AccountSubject(name, label, flags);
    }

    // 资产类
    public final AccountSubject s1001 = _("1001", "现金", DEBIT);
    public final AccountSubject s1002 = _("1002", "银行存款", DEBIT);
    public final AccountSubject s1009 = _("1009", "其他货币资金", DEBIT);
    public final AccountSubject s100901 = _("100901", "外埠存款", DEBIT);
    public final AccountSubject s100902 = _("100902", "银行本票", DEBIT);
    public final AccountSubject s100903 = _("100903", "银行汇票", DEBIT);
    public final AccountSubject s100904 = _("100904", "信用卡", DEBIT);
    public final AccountSubject s100905 = _("100905", "信用证保证金", DEBIT);
    public final AccountSubject s100906 = _("100906", "存出投资款", DEBIT);
    public final AccountSubject s1101 = _("1101", "短期投资", DEBIT);
    public final AccountSubject s110101 = _("110101", "股票", DEBIT);
    public final AccountSubject s110102 = _("110102", "债券", DEBIT);
    public final AccountSubject s110103 = _("110103", "基金", DEBIT);
    public final AccountSubject s110110 = _("110110", "其他", DEBIT);
    public final AccountSubject s1102 = _("1102", "短期投资跌价准备", DEBIT);
    public final AccountSubject s1111 = _("1111", "应收票据", DEBIT);
    public final AccountSubject s1121 = _("1121", "应收股利", DEBIT);
    public final AccountSubject s1122 = _("1122", "应收利息", DEBIT);
    public final AccountSubject s1131 = _("1131", "应收账款", DEBIT);
    public final AccountSubject s1133 = _("1133", "其他应收款", DEBIT);
    public final AccountSubject s1141 = _("1141", "坏账准备", CREDIT);
    public final AccountSubject s1151 = _("1151", "预付账款", DEBIT);
    public final AccountSubject s1161 = _("1161", "应收补贴款", DEBIT);
    public final AccountSubject s1201 = _("1201", "物资采购", DEBIT);
    public final AccountSubject s1211 = _("1211", "原材料", DEBIT);
    public final AccountSubject s1221 = _("1221", "包装物", DEBIT);
    public final AccountSubject s1231 = _("1231", "低值易耗品", DEBIT);
    public final AccountSubject s1232 = _("1232", "材料成本差异", DEBIT);
    public final AccountSubject s1241 = _("1241", "自制半成品", DEBIT);
    public final AccountSubject s1243 = _("1243", "库存商品", DEBIT);
    public final AccountSubject s1244 = _("1244", "商品进销差价", DEBIT);
    public final AccountSubject s1251 = _("1251", "委托加工物资", DEBIT);
    public final AccountSubject s1261 = _("1261", "委托代销商品", DEBIT);
    public final AccountSubject s1271 = _("1271", "受托代销商品", DEBIT);
    public final AccountSubject s1281 = _("1281", "存货跌价准备", DEBIT);
    public final AccountSubject s1291 = _("1291", "分期收款发出商品", DEBIT);
    public final AccountSubject s1301 = _("1301", "待摊费用", DEBIT);
    public final AccountSubject s1401 = _("1401", "长期股权投资", DEBIT);
    public final AccountSubject s140101 = _("140101", "股票投资", DEBIT);
    public final AccountSubject s140102 = _("140102", "其他股权投资", DEBIT);
    public final AccountSubject s1402 = _("1402", "长期债权投资", DEBIT);
    public final AccountSubject s140201 = _("140201", "债券投资", DEBIT);
    public final AccountSubject s140202 = _("140202", "其他债权投资", DEBIT);
    public final AccountSubject s1421 = _("1421", "长期投资减值准备", DEBIT);
    public final AccountSubject s1431 = _("1431", "委托贷款", DEBIT);
    public final AccountSubject s143101 = _("143101", "本金", DEBIT);
    public final AccountSubject s143102 = _("143102", "利息", DEBIT);
    public final AccountSubject s143103 = _("143103", "减值准备", DEBIT);
    public final AccountSubject s1501 = _("1501", "固定资产", DEBIT);
    public final AccountSubject s1502 = _("1502", "累计折旧", CREDIT);
    public final AccountSubject s1505 = _("1505", "固定资产减值准备", DEBIT);
    public final AccountSubject s1601 = _("1601", "工程物资", DEBIT);
    public final AccountSubject s160101 = _("160101", "专用材料", DEBIT);
    public final AccountSubject s160102 = _("160102", "专用设备", DEBIT);
    public final AccountSubject s160103 = _("160103", "预付大型设备款", DEBIT);
    public final AccountSubject s160104 = _("160104", "为生产准备的工具及器具", DEBIT);
    public final AccountSubject s1603 = _("1603", "在建工程", DEBIT);
    public final AccountSubject s1605 = _("1605", "在建工程减值准备", DEBIT);
    public final AccountSubject s1701 = _("1701", "固定资产清理", DEBIT);
    public final AccountSubject s1801 = _("1801", "无形资产", DEBIT);
    public final AccountSubject s1805 = _("1805", "无形资产减值准备", DEBIT);
    public final AccountSubject s1815 = _("1815", "未确认融资费用", DEBIT);
    public final AccountSubject s1901 = _("1901", "长期待摊费用", DEBIT);
    public final AccountSubject s1911 = _("1911", "待处理财产损溢", DEBIT);
    public final AccountSubject s191101 = _("191101", "待处理流动资产损溢", DEBIT);
    public final AccountSubject s191102 = _("191102", "待处理固定资产损溢", DEBIT);

    // 负债类
    public final AccountSubject s2101 = _("2101", "短期借款", CREDIT);
    public final AccountSubject s2111 = _("2111", "应付票据", CREDIT);
    public final AccountSubject s2121 = _("2121", "应付账款", CREDIT);
    public final AccountSubject s2131 = _("2131", "预收账款", CREDIT);
    public final AccountSubject s2141 = _("2141", "代销商品款", CREDIT);
    public final AccountSubject s2151 = _("2151", "应付工资", CREDIT);
    public final AccountSubject s2153 = _("2153", "应付福利费", CREDIT);
    public final AccountSubject s2161 = _("2161", "应付股利", CREDIT);
    public final AccountSubject s2171 = _("2171", "应交税金", CREDIT);
    public final AccountSubject s217101 = _("217101", "应交增值税", CREDIT);
    public final AccountSubject s21710101 = _("21710101", "进项税额", CREDIT);
    public final AccountSubject s21710102 = _("21710102", "已交税金", CREDIT);
    public final AccountSubject s21710103 = _("21710103", "转出未交增值税", CREDIT);
    public final AccountSubject s21710104 = _("21710104", "减免税款", CREDIT);
    public final AccountSubject s21710105 = _("21710105", "销项税额", CREDIT);
    public final AccountSubject s21710106 = _("21710106", "出口退税", CREDIT);
    public final AccountSubject s21710107 = _("21710107", "进项税额转出", CREDIT);
    public final AccountSubject s21710108 = _("21710108", "出口抵减内销产品应纳税额", CREDIT);
    public final AccountSubject s21710109 = _("21710109", "转出多交增值税", CREDIT);
    public final AccountSubject s21710110 = _("21710110", "未交增值税", CREDIT);
    public final AccountSubject s217102 = _("217102", "应交营业税", CREDIT);
    public final AccountSubject s217103 = _("217103", "应交消费税", CREDIT);
    public final AccountSubject s217104 = _("217104", "应交资源税", CREDIT);
    public final AccountSubject s217105 = _("217105", "应交所得税", CREDIT);
    public final AccountSubject s217106 = _("217106", "应交土地增值税", CREDIT);
    public final AccountSubject s217107 = _("217107", "应交城市维护建设税", CREDIT);
    public final AccountSubject s217108 = _("217108", "应交房产税", CREDIT);
    public final AccountSubject s217109 = _("217109", "应交土地使用税", CREDIT);
    public final AccountSubject s217110 = _("217110", "应交车船使用税", CREDIT);
    public final AccountSubject s217111 = _("217111", "应交个人所得税", CREDIT);
    public final AccountSubject s2176 = _("2176", "其他应交款", CREDIT);
    public final AccountSubject s2181 = _("2181", "其他应付款", CREDIT);
    public final AccountSubject s2191 = _("2191", "预提费用", CREDIT);
    public final AccountSubject s2201 = _("2201", "待转资产价值", CREDIT);
    public final AccountSubject s2211 = _("2211", "预计负债", CREDIT);
    public final AccountSubject s2301 = _("2301", "长期借款", CREDIT);
    public final AccountSubject s2311 = _("2311", "应付债券", CREDIT);
    public final AccountSubject s231101 = _("231101", "债券面值", CREDIT);
    public final AccountSubject s231102 = _("231102", "债券溢价", CREDIT);
    public final AccountSubject s231103 = _("231103", "债券折价", CREDIT);
    public final AccountSubject s231104 = _("231104", "应计利息", CREDIT);
    public final AccountSubject s2321 = _("2321", "长期应付款", CREDIT);
    public final AccountSubject s2331 = _("2331", "专项应付款", CREDIT);
    public final AccountSubject s2341 = _("2341", "递延税款", CREDIT);

    // 所有者权益类
    public final AccountSubject s3101 = _("3101", "实收资本(或股本)", CREDIT);
    public final AccountSubject s3103 = _("3103", "已归还投资", CREDIT);
    public final AccountSubject s3111 = _("3111", "资本公积", CREDIT);
    public final AccountSubject s311101 = _("311101", "资本(或股本)溢价", CREDIT);
    public final AccountSubject s311102 = _("311102", "接受捐赠非现金资产准备", CREDIT);
    public final AccountSubject s311103 = _("311103", "接受现金捐赠", CREDIT);
    public final AccountSubject s311104 = _("311104", "股权投资准备", CREDIT);
    public final AccountSubject s311105 = _("311105", "拨款转入", CREDIT);
    public final AccountSubject s311106 = _("311106", "外币资本折算差额", CREDIT);
    public final AccountSubject s311107 = _("311107", "其他资本公积", CREDIT);
    public final AccountSubject s3121 = _("3121", "盈余公积", CREDIT);
    public final AccountSubject s312101 = _("312101", "法定盈余公积", CREDIT);
    public final AccountSubject s312102 = _("312102", "任意盈余公积", CREDIT);
    public final AccountSubject s312103 = _("312103", "法定公益金", CREDIT);
    public final AccountSubject s312104 = _("312104", "储备基金", CREDIT);
    public final AccountSubject s312105 = _("312105", "企业发展基金", CREDIT);
    public final AccountSubject s312106 = _("312106", "利润归还投资", CREDIT);
    public final AccountSubject s3131 = _("3131", "本年利润", CREDIT);
    public final AccountSubject s3141 = _("3141", "利润分配", CREDIT);
    public final AccountSubject s314101 = _("314101", "其他转入", CREDIT);
    public final AccountSubject s314102 = _("314102", "提取法定盈余公积", CREDIT);
    public final AccountSubject s314103 = _("314103", "提取法定公益金", CREDIT);
    public final AccountSubject s314104 = _("314104", "提取储备基金", CREDIT);
    public final AccountSubject s314105 = _("314105", "提取企业发展基金", CREDIT);
    public final AccountSubject s314106 = _("314106", "提取职工奖励及福利基金", CREDIT);
    public final AccountSubject s314107 = _("314107", "利润归还投资", CREDIT);
    public final AccountSubject s314108 = _("314108", "应付优先股股利", CREDIT);
    public final AccountSubject s314109 = _("314109", "提取任意盈余公积", CREDIT);
    public final AccountSubject s314110 = _("314110", "应付普通股股利", CREDIT);
    public final AccountSubject s314111 = _("314111", "转作资本(或股本)的普通股股利", CREDIT);
    public final AccountSubject s314115 = _("314115", "未分配利润", CREDIT);

    // 成本类
    public final AccountSubject s4101 = _("4101", "生产成本", DEBIT);
    public final AccountSubject s410101 = _("410101", "基本生产成本", DEBIT);
    public final AccountSubject s410102 = _("410102", "辅助生产成本", DEBIT);
    public final AccountSubject s4105 = _("4105", "制造费用", DEBIT);
    public final AccountSubject s4107 = _("4107", "劳务成本", DEBIT);

    // 损益类
    public final AccountSubject s5101 = _("5101", "主营业务收入", CREDIT);
    public final AccountSubject s5102 = _("5102", "其他业务收入", CREDIT);
    public final AccountSubject s5201 = _("5201", "投资收益", CREDIT);
    public final AccountSubject s5203 = _("5203", "补贴收入", CREDIT);
    public final AccountSubject s5301 = _("5301", "营业外收入", CREDIT);
    public final AccountSubject s5401 = _("5401", "主营业务成本", DEBIT);
    public final AccountSubject s5402 = _("5402", "主营业务税金及附加", DEBIT);
    public final AccountSubject s5405 = _("5405", "其他业务支出", DEBIT);
    public final AccountSubject s5501 = _("5501", "营业费用", DEBIT);
    public final AccountSubject s5502 = _("5502", "管理费用", DEBIT);
    public final AccountSubject s5503 = _("5503", "财务费用", DEBIT);
    public final AccountSubject s5601 = _("5601", "营业外支出", DEBIT);
    public final AccountSubject s5701 = _("5701", "所得税", DEBIT);
    public final AccountSubject s5801 = _("5801", "以前年度损益调整", DEBIT);

}
