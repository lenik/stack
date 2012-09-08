package com.bee32.sem.asset.entity;

import static com.bee32.sem.asset.entity.AccountSubject.CREDIT;
import static com.bee32.sem.asset.entity.AccountSubject.DEBIT;

import com.bee32.plover.orm.sample.StandardSamples;

public class AccountSubjects
        extends StandardSamples {

    static AccountSubject _(String name, String label, int flags) {
        return new AccountSubject(name, label, flags);
    }

  //资产类
    public final AccountSubject s1001 = _("1001", "库存现金", DEBIT);
    public final AccountSubject s1002 = _("1002", "银行存款", DEBIT);
    public final AccountSubject s1012 = _("1012", "其他货币资金", DEBIT);
    public final AccountSubject s1101 = _("1101", "短期投资", DEBIT);
    public final AccountSubject s1121 = _("1121", "应收票据", DEBIT);
    public final AccountSubject s1122 = _("1122", "应收账款", DEBIT);
    public final AccountSubject s1123 = _("1123", "预付账款", DEBIT);
    public final AccountSubject s1131 = _("1131", "应收股利", DEBIT);
    public final AccountSubject s1132 = _("1132", "应收利息", DEBIT);
    public final AccountSubject s1221 = _("1221", "其他应收款", DEBIT);
    public final AccountSubject s1401 = _("1401", "材料采购", DEBIT);
    public final AccountSubject s1402 = _("1402", "在途物资", DEBIT);
    public final AccountSubject s1403 = _("1403", "原材料", DEBIT);
    public final AccountSubject s1404 = _("1404", "材料成本差异", DEBIT);
    public final AccountSubject s1405 = _("1405", "库存商品", DEBIT);
    public final AccountSubject s1407 = _("1407", "商品进销差价", DEBIT);
    public final AccountSubject s1408 = _("1408", "委托加工物资", DEBIT);
    public final AccountSubject s1411 = _("1411", "周转材料", DEBIT);
    public final AccountSubject s1421 = _("1421", "消耗性生物资产", DEBIT);
    public final AccountSubject s1501 = _("1501", "长期债券投资", DEBIT);
    public final AccountSubject s1511 = _("1511", "长期股权投资", DEBIT);
    public final AccountSubject s1601 = _("1601", "固定资产", DEBIT);
    public final AccountSubject s1602 = _("1602", "累计折旧", CREDIT);
    public final AccountSubject s1604 = _("1604", "在建工程", DEBIT);
    public final AccountSubject s1605 = _("1605", "工程物资", DEBIT);
    public final AccountSubject s1606 = _("1606", "固定资产清理", DEBIT);
    public final AccountSubject s1621 = _("1621", "生产性生物资产", DEBIT);
    public final AccountSubject s1622 = _("1622", "生产性生物资产累计折旧", CREDIT);
    public final AccountSubject s1701 = _("1701", "无形资产", DEBIT);
    public final AccountSubject s1702 = _("1702", "累计摊销", CREDIT);
    public final AccountSubject s1801 = _("1801", "长期待摊费用", DEBIT);
    public final AccountSubject s1901 = _("1901", "待处理财产损溢", DEBIT);

    //负债类
    public final AccountSubject s2001 = _("2001", "短期借款", CREDIT);
    public final AccountSubject s2201 = _("2201", "应付票据", CREDIT);
    public final AccountSubject s2202 = _("2202", "应付账款", CREDIT);
    public final AccountSubject s2203 = _("2203", "预收账款", CREDIT);
    public final AccountSubject s2211 = _("2211", "应付职工薪酬", CREDIT);
    public final AccountSubject s2221 = _("2221", "应交税费", CREDIT);
    public final AccountSubject s2231 = _("2231", "应付利息", CREDIT);
    public final AccountSubject s2232 = _("2232", "应付利润", CREDIT);
    public final AccountSubject s2241 = _("2241", "其他应付款", CREDIT);
    public final AccountSubject s2401 = _("2401", "递延收益", CREDIT);
    public final AccountSubject s2501 = _("2501", "长期借款", CREDIT);
    public final AccountSubject s2701 = _("2701", "长期应付款", CREDIT);

    //经益类
    public final AccountSubject s3001 = _("3001", "实收资本", CREDIT);
    public final AccountSubject s3002 = _("3002", "资本公积", CREDIT);
    public final AccountSubject s3101 = _("3101", "盈余公积", CREDIT);
    public final AccountSubject s3103 = _("3103", "本年利润", CREDIT);
    public final AccountSubject s3104 = _("3104", "利润分配", CREDIT);

    //成本类
    public final AccountSubject s4001 = _("4001", "生产成本", DEBIT);
    public final AccountSubject s4101 = _("4101", "制造费用", DEBIT);
    public final AccountSubject s4301 = _("4301", "研发支出", DEBIT);
    public final AccountSubject s4401 = _("4401", "工程施工", DEBIT);
    public final AccountSubject s4403 = _("4403", "机械作业", DEBIT);

    //损益类
    public final AccountSubject s5001 = _("5001", "主营业务收入", CREDIT);
    public final AccountSubject s5051 = _("5051", "其他业务收入", CREDIT);
    public final AccountSubject s5111 = _("5111", "投资收益", CREDIT);
    public final AccountSubject s5301 = _("5301", "营业外收入", CREDIT);
    public final AccountSubject s5401 = _("5401", "主营业务成本", DEBIT);
    public final AccountSubject s5402 = _("5402", "其他业务成本", DEBIT);
    public final AccountSubject s5403 = _("5403", "营业税金及附加", DEBIT);
    public final AccountSubject s5601 = _("5601", "销售费用", DEBIT);
    public final AccountSubject s5602 = _("5602", "管理费用", DEBIT);
    public final AccountSubject s5603 = _("5603", "财务费用", DEBIT);
    public final AccountSubject s5711 = _("5711", "营业外支出", DEBIT);
    public final AccountSubject s5801 = _("5801", "所得税费用", DEBIT);
}
