package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.plover.orm.util.SampleList;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyles;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSourceTypes;
import com.bee32.sem.chance.entity.ChanceStages;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

public class SEMChanceSamples
        extends NormalSamples {

    public final Chance chance = new Chance();
    public final ChanceParty party = new ChanceParty();
    public final ChanceParty party2 = new ChanceParty();
    public final ChanceAction chanceAction1 = new ChanceAction();
    public final ChanceAction chanceAction2 = new ChanceAction();
    public final ChanceCompetitor competitor = new ChanceCompetitor();

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);
    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
    SEMInventorySamples inventories = predefined(SEMInventorySamples.class);
    ChanceActionStyles actionStyles = predefined(ChanceActionStyles.class);
    ChanceSourceTypes sourceTypes = predefined(ChanceSourceTypes.class);
    ChanceStages chanceStages = predefined(ChanceStages.class);

    @Override
    protected void wireUp() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);

        chance.setAltId("C-001");
        chance.setSubject(PREFIX + "初号机发射塔");
        // chance.setCreatedDate(cal.getTime());
        chance.setSource(sourceTypes.DEVELOP);
        chance.setContent("需要专门的安防系统，能够在浦东的销售中心直接监控闵行区厂房实时录像和声音");

        party.setChance(chance);
        party.setParty(people.bugatti);
        party.setRole("main");

        party2.setChance(chance);
        party2.setParty(people.bentley);
        party2.setRole("subordinate");

        chance.setParties(Arrays.asList(party, party2));

        competitor.setLabel(PREFIX + "第三使徒");
        competitor.setChance(chance);
        competitor.setTactic("在北京、上海、广州三地召开大规模的产品发布会");
        competitor.setComment("柳良的朋友，关系很好");
        competitor.setAdvantage("产品线95%齐全；产品质量高，市场认可度高");
        competitor.setDisvantage(//
                "管理人员缺少经验、能力; 团队精神差，缺少沟通；职责不清楚");
        competitor.setPrice(new MCValue(null, 5000.0));
        competitor.setCapability("核心竞争力");
        competitor.setSolution("目前没有好的解决方案");

        chanceAction1.setChance(chance);
        chanceAction1.setBeginTime(cal.getTime());
        chanceAction1.setEndTime(cal.getTime());
        chanceAction1.setMoreInfo("在北京、上海、广州三个重点区域发展8至10家大OEM厂商");
        chanceAction1.setSpending("打的15, 吃饭30,住宿100,共145");
        chanceAction1.setActor(principals.eva);
        chanceAction1.setStyle(actionStyles.INTERNET);
        chanceAction1.setParties(Arrays.<Party> asList(people.bentley, people.bugatti));
        chanceAction1.setStage(chanceStages.INIT);

        chanceAction2.setBeginTime(cal.getTime());
        chanceAction2.setEndTime(cal.getTime());
        chanceAction2.setMoreInfo("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        chanceAction2.setSpending("spendingspendingspendingspendingspendingspending");
        chanceAction2.setActor(principals.eva);
        chanceAction2.setStyle(actionStyles.TALK);
        chanceAction2.setParties(Arrays.asList((Party) people.weiXiaoBao));

        chance.addAction(chanceAction1);
        chance.addAction(chanceAction2);
    }

    @Override
    protected void getSamples(SampleList samples) {
        // add <price>->quotionDetail
        samples.add(chance);
        samples.add(party);
        samples.add(party2);
        samples.add(competitor);
        samples.add(chanceAction1);
        samples.add(chanceAction2);
    }

}
