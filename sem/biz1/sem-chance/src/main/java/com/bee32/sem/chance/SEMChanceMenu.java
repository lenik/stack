package com.bee32.sem.chance;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.dto.ChanceSourceTypeDto;
import com.bee32.sem.chance.dto.ProcurementMethodDto;
import com.bee32.sem.chance.dto.PurchaseRegulationDto;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ProcurementMethod;
import com.bee32.sem.chance.entity.PurchaseRegulation;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMChanceMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMChanceModule.PREFIX_);

    public transient MenuNode CHANCE = _frame_.BIZ1;

    MenuNode chance = entry(CHANCE, 30, "chance", prefix.join("chance/"));
    MenuNode action = entry(CHANCE, 31, "action", prefix.join("action/"));

    public MenuNode CHANCE_DICT = menu(CHANCE, 32, "CHANCE_DICT");
    /*    */MenuNode category = entry(CHANCE_DICT, 1, "category",
            simpleDictIndex("机会分类", ChanceCategory.class, ChanceCategoryDto.class));
    /*    */MenuNode sourceType = entry(CHANCE_DICT, 2, "sourceType",
            simpleDictIndex("销售机会来源", ChanceSourceType.class, ChanceSourceTypeDto.class));
    /*    */MenuNode actionStyle = entry(CHANCE_DICT, 3, "actionStyle",
            simpleDictIndex("洽谈类型", ChanceActionStyle.class, ChanceActionStyleDto.class));
    /*    */MenuNode procurementMethod = entry(CHANCE_DICT, 5, "procurementMethod",
            simpleDictIndex("供货方式", ProcurementMethod.class, ProcurementMethodDto.class));
    /*    */MenuNode purchaseRegulation = entry(CHANCE_DICT, 6, "purchaseRegulation",
            simpleDictIndex("采购原则", PurchaseRegulation.class, PurchaseRegulationDto.class));
    /*    */MenuNode stage = entry(CHANCE_DICT, 4, "stage", prefix.join("stage/"));

    public MenuNode PRICE = menu(CHANCE, 32, "PRICE");

    // MenuNode competitor = entry(CHANCE, "competitor", CHANCE_.join("competitor/ "));

}
