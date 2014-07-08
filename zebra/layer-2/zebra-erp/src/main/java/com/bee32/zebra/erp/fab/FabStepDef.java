package com.bee32.zebra.erp.fab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.zebra.erp.stock.Artifact;
import com.tinylily.model.base.CoCode;

public class FabStepDef
        extends CoCode<FabStepDef> {

    private static final long serialVersionUID = 1L;

    public static final int BOM = 1;
    public static final int TECHNIQUE = 2;
    public static final int INTERNAL = 3;
    int visibility = BOM;

    Artifact output;
    FabOpCode opCode;
    Map<Artifact, Double> input;

    int duration; // in seconds
    double cost;
    Map<FabCostCode, Double> costMap = new HashMap<>();

    FabDevice device;
    String tools;
    List<FabQcDef> qcSpec = new ArrayList<FabQcDef>();

}
