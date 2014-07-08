package com.bee32.zebra.erp.fab;

import java.util.Date;
import java.util.List;

import com.bee32.zebra.erp.stock.Artifact;
import com.bee32.zebra.oa.model.contact.Person;
import com.tinylily.model.base.CoMomentInterval;

public class FabStep
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int S_WORKING = 1;
    public static final int S_FINISHED = 2;
    public static final int S_FAILED = 3;

    Artifact product;
    FabStepDef def;
    Date deadline;

    double plan;
    double produced;
    // 合格品
    double qualified;
    // 料废：即来料问题造成产品报废，不是加工人员的问题。
    double scrap0;
    // 工废：即人为造成，比如工作质量不好，工作大意，没有按照要求就行加工等
    double scrap1;

    Person operator;
    Person checker;
    // Person manager;

    List<FabQcValue> qcList;

}
