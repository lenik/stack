package com.bee32.sem.chance;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.chance.entity.*;
import com.bee32.sem.people.SEMPeopleSamples;

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
