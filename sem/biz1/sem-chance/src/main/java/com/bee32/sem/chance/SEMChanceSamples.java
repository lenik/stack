package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.Competitor;

@ImportSamples({ SEMOrgSamples.class, SEMCustomerSamples.class })
public class SEMChanceSamples
        extends EntitySamplesContribution {

    public static Opportunity chance;
    public static ChanceParty parties;
    public static OpportunityHistory action = new OpportunityHistory();
    public static Competitor competitor = new Competitor();

    static {

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);
        chance = new Opportunity();
        chance.setTitle("新办公楼布线及机房设备安装");
        chance.setResponsible(SEMOrgSamples.admin);
        chance.setCreateDate(cal.getTime());
        chance.setSource("网络");
        chance.setContent("");
        chance.setCategory("分类一");
        chance.setStatus("跟踪");

        parties = new ChanceParty();
        parties.setSalesChance(chance);
        parties.setCustomer(SEMCustomerSamples.bukadi);
        parties.setCategory("main");

        chance.setDetails(Arrays.asList(parties));

        competitor.setName("皇冠公司");
        competitor.setSalesChance(chance);
        competitor.setTactic("在北京、上海、广州三地召开大规模的产品发布会，并在杭州及广州各聘用一名销售工程师");
        competitor.setRemark("");
        competitor.setAdvantage("产品线95%齐全；产品质量高，市场认可度高；价格战略被市场接受；库存齐全");
        competitor.setDisvantage(//
                "缺少管理人员落实战略；管理人员缺少经验、能力; 团队精神差，缺少沟通；职责不清楚，各自推卸责任；零配件不全，影响售后服务");
        competitor.setPrice("5000");
        competitor.setCapability("核心竞争力");

        action.setSalesChance(chance);
        action.setDate(cal.getTime());
        action.setDescription("在北京、上海、广州三个重点区域发展10家经销商，再发展8至10家大OEM厂商");
        action.setEnforcer(SEMOrgSamples.admin);
        action.setChanceStatus("成功");
        action.setCategory("上门");
        action.setSubject("业务洽谈");

    }

    @Override
    protected void preamble() {
        addNormalSample(chance);
        addNormalSample(parties);
        addNormalSample(competitor);
        addNormalSample(action);
    }

}
