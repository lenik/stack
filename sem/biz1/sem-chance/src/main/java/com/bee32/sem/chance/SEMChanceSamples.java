package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.Competitor;

@ImportSamples({SEMOrgSamples.class, SEMCustomerSamples.class})
public class SEMChanceSamples
        extends EntitySamplesContribution {

    public static Opportunity salesChance;
    public static ChanceParty details;
    public static OpportunityHistory actionHistory = new OpportunityHistory();
    public static Competitor competitor = new Competitor();


    static {

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);
        salesChance = new Opportunity();
        salesChance.setTitle("新办公楼布线及机房设备安装");
        salesChance.setResponsible(SEMOrgSamples.admin);
        salesChance.setCreateDate(cal.getTime());
        salesChance.setSource("网络");
        salesChance.setContent("");
        salesChance.setCategory("分类一");
        salesChance.setStatus("跟踪");

        details = new ChanceParty();
        details.setSalesChance(salesChance);
        details.setCustomer(SEMCustomerSamples.bukadi);
        details.setCategory("main");

        salesChance.setDetails(Arrays.asList(details));


        competitor.setName("皇冠公司");
        competitor.setSalesChance(salesChance);
        competitor.setTactic("在北京、上海、广州三地召开大规模的产品发布会，并在杭州及广州各聘用一名销售工程师");
        competitor.setRemark("");
        competitor.setAdvantage("产品线95%齐全；产品质量高，市场认可度高；价格战略被市场接受；库存齐全");
        competitor.setDisvantage(//
                "缺少管理人员落实战略；管理人员缺少经验、能力; 团队精神差，缺少沟通；职责不清楚，各自推卸责任；零配件不全，影响售后服务");
        competitor.setPrice("5000");
        competitor.setCapability("核心竞争力");

        actionHistory.setSalesChance(salesChance);
        actionHistory.setDate(cal.getTime());
        actionHistory.setDescription("在北京、上海、广州三个重点区域发展10家经销商，再发展8至10家大OEM厂商");
        actionHistory.setEnforcer(SEMOrgSamples.admin);
        actionHistory.setChanceStatus("成功");
        actionHistory.setCategory("上门");
        actionHistory.setSubject("业务洽谈");

    }

    @Override
    protected void preamble() {

        addNormalSample(salesChance);
        addNormalSample(details);
        addNormalSample(competitor);
        addNormalSample(actionHistory);

    }

}
