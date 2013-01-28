package com.bee32.sem.chance;

import java.util.Arrays;
import java.util.Calendar;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyles;
import com.bee32.sem.chance.entity.ChanceCategories;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSourceTypes;
import com.bee32.sem.chance.entity.ChanceStages;
import com.bee32.sem.chance.entity.ProcurementMethods;
import com.bee32.sem.chance.entity.PurchaseRegulations;
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
    ChanceActionStyles actionStyles = predefined(ChanceActionStyles.class);
    ChanceSourceTypes sourceTypes = predefined(ChanceSourceTypes.class);
    ChanceStages chanceStages = predefined(ChanceStages.class);
    PurchaseRegulations purchaseRegulations = predefined(PurchaseRegulations.class);
    ProcurementMethods procurementMethods = predefined(ProcurementMethods.class);
    ChanceCategories chanceCategories = predefined(ChanceCategories.class);

    @Override
    protected void wireUp() {

    }

}
