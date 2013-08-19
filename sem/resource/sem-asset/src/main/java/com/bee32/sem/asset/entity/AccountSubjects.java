package com.bee32.sem.asset.entity;

import static com.bee32.sem.asset.entity.AccountSubject.CREDIT;
import static com.bee32.sem.asset.entity.AccountSubject.DEBIT;

import com.bee32.plover.orm.sample.StandardSamples;

/**
 * 预置会计科目
 *
 * 预置会计科目,对应2013年小企业会计准则
 *
 * <p lang="en">
 * Predefined Account Subjects
 */
public class AccountSubjects
        extends StandardSamples {

    static AccountSubject _(String name, String label, int flags) {
        return new AccountSubject(name, label, flags);
    }

    // 资产类
    public final AccountSubject s1001 = _("1001", "库存现金", DEBIT);
    public final AccountSubject s1002 = _("1002", "银行存款", DEBIT);
    public final AccountSubject s1012 = _("1012", "其他货币资金", DEBIT);
    public final AccountSubject s1101 = _("1101", "短期投资", DEBIT);
    public final AccountSubject s110101 = _("110101", "股票", DEBIT);
    public final AccountSubject s110102 = _("110102", "债券", DEBIT);
    public final AccountSubject s110103 = _("110103", "基金", DEBIT);
    public final AccountSubject s110104 = _("110104", "其他", DEBIT);
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

    // 负债类
    public final AccountSubject s2001 = _("2001", "短期借款", CREDIT);
    public final AccountSubject s2201 = _("2201", "应付票据", CREDIT);
    public final AccountSubject s2202 = _("2202", "应付账款", CREDIT);
    public final AccountSubject s2203 = _("2203", "预收账款", CREDIT);
    public final AccountSubject s2211 = _("2211", "应付职工薪酬", CREDIT);
    public final AccountSubject s221101 = _("221101", "应付职工工资", CREDIT);
    public final AccountSubject s221102 = _("221102", "应付奖金、津贴和补贴", CREDIT);
    public final AccountSubject s221103 = _("221103", "应付福利费", CREDIT);
    public final AccountSubject s221104 = _("221104", "应付社会保险费", CREDIT);
    public final AccountSubject s221105 = _("221105", "应付住房公积金", CREDIT);
    public final AccountSubject s221106 = _("221106", "应付工会经费", CREDIT);
    public final AccountSubject s221107 = _("221107", "应付教育经费", CREDIT);
    public final AccountSubject s221108 = _("221108", "非货币性福利", CREDIT);
    public final AccountSubject s221109 = _("221109", "辞退福利", CREDIT);
    public final AccountSubject s221110 = _("221110", "其他应付职工薪酬", CREDIT);
    public final AccountSubject s2221 = _("2221", "应交税费", CREDIT);
    public final AccountSubject s222101 = _("222101", "应交增值税", CREDIT);
    public final AccountSubject s22210101 = _("22210101", "进项税额", CREDIT);
    public final AccountSubject s22210102 = _("22210102", "销项税额", CREDIT);
    public final AccountSubject s222102 = _("222102", "未交增值税", CREDIT);
    public final AccountSubject s222103 = _("222103", "应交营业税", CREDIT);
    public final AccountSubject s222104 = _("222104", "应交消费税", CREDIT);
    public final AccountSubject s222105 = _("222105", "应交资源税", CREDIT);
    public final AccountSubject s222106 = _("222106", "应交所得税", CREDIT);
    public final AccountSubject s222107 = _("222107", "应交土地增值税", CREDIT);
    public final AccountSubject s222108 = _("222108", "应交城市维护建设税", CREDIT);
    public final AccountSubject s222109 = _("222109", "应交房产税", CREDIT);
    public final AccountSubject s222110 = _("222110", "应交城镇土地使用税", CREDIT);
    public final AccountSubject s222111 = _("222111", "应交车船使用税", CREDIT);
    public final AccountSubject s222112 = _("222112", "应交个人所得税", CREDIT);
    public final AccountSubject s222113 = _("222113", "教育费附加", CREDIT);
    public final AccountSubject s222114 = _("222114", "矿产资源补偿费", CREDIT);
    public final AccountSubject s222115 = _("222115", "排污费", CREDIT);
    public final AccountSubject s222116 = _("222116", "印花税", CREDIT);
    public final AccountSubject s2231 = _("2231", "应付利息", CREDIT);
    public final AccountSubject s2232 = _("2232", "应付利润", CREDIT);
    public final AccountSubject s2241 = _("2241", "其他应付款", CREDIT);
    public final AccountSubject s2401 = _("2401", "递延收益", CREDIT);
    public final AccountSubject s2501 = _("2501", "长期借款", CREDIT);
    public final AccountSubject s2701 = _("2701", "长期应付款", CREDIT);

    // 经益类
    public final AccountSubject s3001 = _("3001", "实收资本", CREDIT);
    public final AccountSubject s3002 = _("3002", "资本公积", CREDIT);
    public final AccountSubject s3101 = _("3101", "盈余公积", CREDIT);
    public final AccountSubject s310101 = _("310101", "法定盈余公积", CREDIT);
    public final AccountSubject s310102 = _("310102", "任意盈余公积", CREDIT);
    public final AccountSubject s3103 = _("3103", "本年利润", CREDIT);
    public final AccountSubject s3104 = _("3104", "利润分配", CREDIT);
    public final AccountSubject s310401 = _("310401", "其他转入", CREDIT);
    public final AccountSubject s310402 = _("310402", "提取法定盈余公积", CREDIT);
    public final AccountSubject s310403 = _("310403", "提取法定公益金", CREDIT);
    public final AccountSubject s310404 = _("310404", "提取职工奖励及福利基金", CREDIT);
    public final AccountSubject s310409 = _("310409", "提取任意盈余公积", CREDIT);
    public final AccountSubject s310410 = _("310410", "应付利润", CREDIT);
    public final AccountSubject s310415 = _("310415", "未分配利润", CREDIT);

    // 成本类
    public final AccountSubject s4001 = _("4001", "生产成本", DEBIT);
    public final AccountSubject s4101 = _("4101", "制造费用", DEBIT);
    public final AccountSubject s4301 = _("4301", "研发支出", DEBIT);
    public final AccountSubject s4401 = _("4401", "工程施工", DEBIT);
    public final AccountSubject s4403 = _("4403", "机械作业", DEBIT);

    // 损益类
    public final AccountSubject s5001 = _("5001", "主营业务收入", CREDIT);
    public final AccountSubject s5051 = _("5051", "其他业务收入", CREDIT);
    public final AccountSubject s5111 = _("5111", "投资收益", CREDIT);
    public final AccountSubject s5301 = _("5301", "营业外收入", CREDIT);
    public final AccountSubject s530101 = _("530101", "政府补助", CREDIT);
    public final AccountSubject s530102 = _("530102", "收回坏账损失", CREDIT);
    public final AccountSubject s530103 = _("530103", "汇兑收益", CREDIT);
    public final AccountSubject s530104 = _("530104", "非流动资产处置净收益", CREDIT);
    public final AccountSubject s5401 = _("5401", "主营业务成本", DEBIT);
    public final AccountSubject s5402 = _("5402", "其他业务成本", DEBIT);
    public final AccountSubject s5403 = _("5403", "营业税金及附加", DEBIT);
    public final AccountSubject s5601 = _("5601", "销售费用", DEBIT);
    public final AccountSubject s560101 = _("560101", "商品维修费", DEBIT);
    public final AccountSubject s560102 = _("560102", "广告费", DEBIT);
    public final AccountSubject s560103 = _("560103", "业务宣传费", DEBIT);
    public final AccountSubject s560104 = _("560104", "交通费", DEBIT);
    public final AccountSubject s560105 = _("560105", "通讯费", DEBIT);
    public final AccountSubject s560106 = _("560106", "业务招待费", DEBIT);
    public final AccountSubject s560107 = _("560107", "员工工资", DEBIT);
    public final AccountSubject s5602 = _("5602", "管理费用", DEBIT);
    public final AccountSubject s560201 = _("560201", "开办费", DEBIT);
    public final AccountSubject s560202 = _("560202", "业务招待费", DEBIT);
    public final AccountSubject s560203 = _("560203", "研究费用", DEBIT);
    public final AccountSubject s560204 = _("560204", "交通费", DEBIT);
    public final AccountSubject s560205 = _("560205", "通讯费", DEBIT);
    public final AccountSubject s560206 = _("560206", "水电费", DEBIT);
    public final AccountSubject s560207 = _("560207", "房屋租赁费", DEBIT);
    public final AccountSubject s560208 = _("560208", "员工活动费", DEBIT);
    public final AccountSubject s560209 = _("560209", "员工工资", DEBIT);
    public final AccountSubject s560210 = _("560210", "折旧", DEBIT);
    public final AccountSubject s560211 = _("560211", "无形资产摊销", DEBIT);
    public final AccountSubject s5603 = _("5603", "财务费用", DEBIT);
    public final AccountSubject s560301 = _("560301", "利息费用", DEBIT);
    public final AccountSubject s560302 = _("560302", "手续费用", DEBIT);
    public final AccountSubject s560303 = _("560303", "现金折扣", DEBIT);
    public final AccountSubject s560304 = _("560304", "汇兑损失", DEBIT);
    public final AccountSubject s5711 = _("5711", "营业外支出", DEBIT);
    public final AccountSubject s571101 = _("571101", "坏账损失", DEBIT);
    public final AccountSubject s571102 = _("571102", "无法收回的长期债券投资损失", DEBIT);
    public final AccountSubject s571103 = _("571103", "无法收回的长期股权投资损失", DEBIT);
    public final AccountSubject s571104 = _("571104", "自然灾害等不可抗力因素造成的损失", DEBIT);
    public final AccountSubject s571105 = _("571105", "税收滞纳金", DEBIT);
    public final AccountSubject s5801 = _("5801", "所得税费用", DEBIT);
}
