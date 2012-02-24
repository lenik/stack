package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import javax.inject.Inject;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

@ImportSamples({ SEMInventorySamples.class })
public class SEMChanceSamples
        extends SampleContribution {

    public final Chance chance = new Chance();
    public final ChanceParty party = new ChanceParty();
    public final ChanceParty party2 = new ChanceParty();
    public final ChanceAction chanceAction1 = new ChanceAction();
    public final ChanceAction chanceAction2 = new ChanceAction();
    public final ChanceCompetitor competitor = new ChanceCompetitor();
    public final ChanceQuotation quotation = new ChanceQuotation();

    @Inject
    IcsfPrincipalSamples principals;

    @Inject
    SEMPeopleSamples people;

    @Inject
    SEMInventorySamples inventories;

    @Override
    protected void preamble() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 4, 8);

        chance.setSerial("CHJFST-00001");
        chance.setSubject("初号机发射塔");
        // chance.setCreatedDate(cal.getTime());
        chance.setSource(ChanceSourceType.DEVELOP);
        chance.setContent("需要专门的安防系统，能够在浦东的销售中心直接监控闵行区厂房实时录像和声音");
        chance.setCategory(ChanceCategory.NORMAL);

        party.setChance(chance);
        party.setParty(people.bugatti);
        party.setRole("main");

        party2.setChance(chance);
        party2.setParty(people.bentley);
        party2.setRole("subordinate");

        chance.setParties(Arrays.asList(party, party2));

        competitor.setLabel("第三使徒");
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
        chanceAction1.setStyle(ChanceActionStyle.INTERNET);
        chanceAction1.setParties(Arrays.<Party> asList(people.bentley, people.bugatti));
        chanceAction1.setStage(ChanceStage.INIT);

        chanceAction2.setBeginTime(cal.getTime());
        chanceAction2.setEndTime(cal.getTime());
        chanceAction2.setMoreInfo("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        chanceAction2.setSpending("spendingspendingspendingspendingspendingspending");
        chanceAction2.setActor(principals.eva);
        chanceAction2.setStyle(ChanceActionStyle.TALK);
        chanceAction2.setParties(Arrays.asList((Party) people.weiXiaoBao));

        chance.addAction(chanceAction1);
        chance.addAction(chanceAction2);

        quotation.setLabel("7月2号报价");
        quotation.setChance(chance);
        quotation.setDeliverInfo("发顺丰加保价");
        quotation.setPayment("网上转帐");
        quotation.addItem(inventories.m_light_A, //
                inventories.m_light_A.getLatestPrice().getPrice(), //
                new BigDecimal(3), 1.0f);

        // add <price>->quotionDetail
        add(chance);
        add(party);
        add(party2);
        add(competitor);
        add(chanceAction1);
        add(chanceAction2);

        // add <price>->quotation and quotationItem
        add(quotation);
    }

}
