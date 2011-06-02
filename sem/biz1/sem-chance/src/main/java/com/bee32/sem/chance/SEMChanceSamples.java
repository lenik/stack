package com.bee32.sem.chance;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSource;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.entity.Competitor;

@ImportSamples({ SEMOrgSamples.class, SEMCustomerSamples.class })
public class SEMChanceSamples
        extends EntitySamplesContribution {

    public static Chance chance;
    public static ChanceParty party;
    public static ChanceAction action = new ChanceAction();
    public static Competitor competitor = new Competitor();

    static {

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);
        chance = new Chance();
        chance.setSubject("新办公楼布线及机房设备安装");
        chance.setOwner(SEMOrgSamples.admin);
        chance.setCreateDate(cal.getTime());
        chance.setSource(ChanceSource.DEVELOP);
        chance.setContent("");
        chance.setCategory(ChanceCategory.NORMAL);

        Set<ChanceParty> parties = new HashSet<ChanceParty>();
        party = new ChanceParty();
        party.setChance(chance);
        party.setCustomer(SEMCustomerSamples.bukadi);
        party.setCategory("main");

        parties.add(party);

        chance.setParties(parties);

        competitor.setName("皇冠公司");
        competitor.setChance(chance);
        competitor.setTactic("在北京、上海、广州三地召开大规模的产品发布会，并在杭州及广州各聘用一名销售工程师");
        competitor.setRemark("");
        competitor.setAdvantage("产品线95%齐全；产品质量高，市场认可度高；价格战略被市场接受；库存齐全");
        competitor.setDisvantage(//
                "缺少管理人员落实战略；管理人员缺少经验、能力; 团队精神差，缺少沟通；职责不清楚，各自推卸责任；零配件不全，影响售后服务");
        competitor.setPrice(5000.0);
        competitor.setCapability("核心竞争力");


        action.setChance(chance);
        action.setBeginTime(cal.getTime());
        action.setEndTime(cal.getTime());
        action.setContent("在北京、上海、广州三个重点区域发展10家经销商，再发展8至10家大OEM厂商");
        action.setStage(ChanceStage.INITIAL);
        action.setSpending("打的 15, 吃饭30,住宿100,共145");

    }

    @Override
    protected void preamble() {
        // add ChanceActionStyle
        addNormalSample(ChanceActionStyle.INTERNET);
        addNormalSample(ChanceActionStyle.TALK);
        addNormalSample(ChanceActionStyle.TELEPHONE);

        // add ChanceCategory
        addNormalSample(ChanceCategory.IMPORTANT);
        addNormalSample(ChanceCategory.NORMAL);
        addNormalSample(ChanceCategory.OTHER);

        // add ChanceSource
        addNormalSample(ChanceSource.AGENT);
        addNormalSample(ChanceSource.CUSTOMER);
        addNormalSample(ChanceSource.DEVELOP);
        addNormalSample(ChanceSource.INTERNET);
        addNormalSample(ChanceSource.INTRODUCTION);
        addNormalSample(ChanceSource.MEDIA);
        addNormalSample(ChanceSource.OTHER);
        addNormalSample(ChanceSource.PARTNER);
        addNormalSample(ChanceSource.PROMOTION);
        addNormalSample(ChanceSource.TELEPHONE);
        addNormalSample(ChanceSource.TENDER);

        // addChanceStage
        addNormalSample(ChanceStage.CONTRACT);
        addNormalSample(ChanceStage.INITIAL);
        addNormalSample(ChanceStage.MEAT);
        addNormalSample(ChanceStage.PAYMENT);
        addNormalSample(ChanceStage.QUOTATION);

        addNormalSample(chance);
        addNormalSample(party);
        addNormalSample(competitor);
        addNormalSample(action);
    }

}
