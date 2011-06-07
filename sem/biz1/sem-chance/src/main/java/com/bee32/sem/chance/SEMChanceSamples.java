package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.entity.Competitor;
import com.bee32.sem.people.SEMPeopleSamples;

@ImportSamples({ SEMPeopleSamples.class, IcsfPrincipalSamples.class })
public class SEMChanceSamples
        extends EntitySamplesContribution {

    public static Chance chance = new Chance();
    public static ChanceParty party = new ChanceParty();
    public static ChanceAction action = new ChanceAction();
    public static Competitor competitor = new Competitor();

    static {

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);
        chance.setSubject("新办公楼布线及机房设备安装");
        chance.setOwner(IcsfPrincipalSamples.eva);
        chance.setCreateDate(cal.getTime());
        chance.setSource(ChanceSourceType.DEVELOP);
        chance.setContent("需要专门的安防系统，能够在浦东的销售中心直接监控闵行区厂房实时录像和声音");
        chance.setCategory(ChanceCategory.NORMAL);

        party.setChance(chance);
        party.setParty(SEMPeopleSamples.one77);
        party.setRole("main");

        chance.setParties(Arrays.asList(party));

        competitor.setName("皇冠公司");
        competitor.setChance(chance);
        competitor.setTactic("在北京、上海、广州三地召开大规模的产品发布会");
        competitor.setRemark("柳良的朋友，关系很好");
        competitor.setAdvantage("产品线95%齐全；产品质量高，市场认可度高");
        competitor.setDisvantage(//
                "管理人员缺少经验、能力; 团队精神差，缺少沟通；职责不清楚");
        competitor.setPrice(5000.0);
        competitor.setCapability("核心竞争力");
        competitor.setSolution("目前没有好的解决方案");

        action.setChance(chance);
        action.setBeginTime(cal.getTime());
        action.setEndTime(cal.getTime());
        action.setContent("在北京、上海、广州三个重点区域发展8至10家大OEM厂商");
        action.setStage(ChanceStage.INITIAL);
        action.setSpending("打的 15, 吃饭30,住宿100,共145");
        action.setActor(IcsfPrincipalSamples.eva);
        action.setStyle(ChanceActionStyle.INTERNET);
//        action.setParties();

        chance.setActions(Arrays.asList(action));

    }

    @Override
    protected void preamble() {

        // addChanceStage
        addNormalSample(ChanceStage.CONTRACT);
        addNormalSample(ChanceStage.INITIAL);
        addNormalSample(ChanceStage.MEAT);
        addNormalSample(ChanceStage.PAYMENT);
        addNormalSample(ChanceStage.QUOTATION);

        // add ChanceActionStyle
        addNormalSample(ChanceActionStyle.INTERNET);
        addNormalSample(ChanceActionStyle.TALK);
        addNormalSample(ChanceActionStyle.TELEPHONE);

        // add ChanceCategory
        addNormalSample(ChanceCategory.IMPORTANT);
        addNormalSample(ChanceCategory.NORMAL);
        addNormalSample(ChanceCategory.OTHER);

        // add ChanceSource
        addNormalSample(ChanceSourceType.AGENT);
        addNormalSample(ChanceSourceType.CUSTOMER);
        addNormalSample(ChanceSourceType.DEVELOP);
        addNormalSample(ChanceSourceType.INTERNET);
        addNormalSample(ChanceSourceType.INTRODUCTION);
        addNormalSample(ChanceSourceType.MEDIA);
        addNormalSample(ChanceSourceType.OTHER);
        addNormalSample(ChanceSourceType.PARTNER);
        addNormalSample(ChanceSourceType.PROMOTION);
        addNormalSample(ChanceSourceType.TELEPHONE);
        addNormalSample(ChanceSourceType.TENDER);

        addNormalSample(chance);
        addNormalSample(party);
        addNormalSample(competitor);
        addNormalSample(action);
    }

}
