package com.bee32.sem.chance.web;

import java.io.Serializable;
import java.util.List;

import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean editable;
    private ChanceDto selectedChance;
    private ChanceDto chance;
    private List<ChanceAction> actions;
    private ChanceAction selectedAction;


}
