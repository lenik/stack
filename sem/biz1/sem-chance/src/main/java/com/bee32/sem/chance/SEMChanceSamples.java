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
import com.bee32.sem.people.entity.Party;

@ImportSamples({ SEMPeopleSamples.class, IcsfPrincipalSamples.class })
public class SEMChanceSamples
        extends EntitySamplesContribution {

    public static Chance chance = new Chance();
    public static ChanceParty party = new ChanceParty();
    public static ChanceParty party2 = new ChanceParty();
    public static ChanceAction chanceAction1 = new ChanceAction();
    public static ChanceAction chanceAction2 = new ChanceAction();
    public static Competitor competitor = new Competitor();

    static {

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);
        chance.setSubject("新办公楼布线及机房设备安装");
        chance.setOwner(IcsfPrincipalSamples.eva);
        // chance.setCreatedDate(cal.getTime());
        chance.setSource(ChanceSourceType.DEVELOP);
        chance.setContent("需要专门的安防系统，能够在浦东的销售中心直接监控闵行区厂房实时录像和声音");
        chance.setCategory(ChanceCategory.NORMAL);

        party.setChance(chance);
        party.setParty(SEMPeopleSamples.one77);
        party.setRole("main");

        party2.setChance(chance);
        party2.setParty(SEMPeopleSamples.bentley);
        party2.setRole("subordinate");

        chance.setParties(Arrays.asList(party, party2));

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

        chanceAction1.setChance(chance);
        chanceAction1.setBeginTime(cal.getTime());
        chanceAction1.setEndTime(cal.getTime());
        chanceAction1.setContent("在北京、上海、广州三个重点区域发展8至10家大OEM厂商");
        chanceAction1.setSpending("打的15, 吃饭30,住宿100,共145");
        chanceAction1.setActor(IcsfPrincipalSamples.eva);
        chanceAction1.setStyle(ChanceActionStyle.INTERNET);
        chanceAction1.setParties(Arrays.asList((Party) SEMPeopleSamples.bentley, (Party) SEMPeopleSamples.one77));
        chanceAction1.setStage(ChanceStage.INIT);

        chanceAction2.setBeginTime(cal.getTime());
        chanceAction2.setEndTime(cal.getTime());
        chanceAction2.setContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        chanceAction2.setSpending("spendingspendingspendingspendingspendingspending");
        chanceAction2.setActor(IcsfPrincipalSamples.eva);
        chanceAction2.setStyle(ChanceActionStyle.TALK);
        chanceAction2.setParties(Arrays.asList((Party) SEMPeopleSamples.weiXiaoBao));

        chance.setActions(Arrays.asList(chanceAction1, chanceAction2));

    }

    @Override
    protected void preamble() {

        // addChanceStage
        addNormalSample(ChanceStage.INIT);
        addNormalSample(ChanceStage.LAUNCHED);
        addNormalSample(ChanceStage.MEETING);
        addNormalSample(ChanceStage.PAYMENT);
        addNormalSample(ChanceStage.QUOTED);
        addNormalSample(ChanceStage.DONE);
        addNormalSample(ChanceStage.TERMINATED);

        // add ChanceActionStyle
        addNormalSample(ChanceActionStyle.INTERNET);
        addNormalSample(ChanceActionStyle.TALK);
        addNormalSample(ChanceActionStyle.TELEPHONE);
        addNormalSample(ChanceActionStyle.OTHER);

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
        addNormalSample(ChanceSourceType.PARTNER);
        addNormalSample(ChanceSourceType.PROMOTION);
        addNormalSample(ChanceSourceType.TELEPHONE);
        addNormalSample(ChanceSourceType.TENDER);
        addNormalSample(ChanceSourceType.OTHER);

        addNormalSample(chance);
        addNormalSample(party);
        addNormalSample(party2);
        addNormalSample(competitor);
        addNormalSample(chanceAction1);
        addNormalSample(chanceAction2);
    }

}
