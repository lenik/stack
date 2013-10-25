package com.bee32.sem.chance;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ProcurementMethod;
import com.bee32.sem.chance.entity.PurchaseRegulation;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 *
 *
 * <p lang="en">
 * SEM Chance Menu
 */
public class SEMChanceMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMChanceModule.PREFIX_);

    /**
     * 销售管理
     *
     * <p lang="en">
     */
    public MenuNode SALEMGMT = menu(_frame_.MAIN, 350, "SALEMGMT");

    /**
     * 机会设置
     *
     * <p lang="en">
     * Chance Dicts
     */
    public MenuNode CHANCE_DICT = menu(SALEMGMT, 1, "CHANCE_DICT");

    /**
     * 机会分类
     *
     * <p lang="en">
     * Chance Category
     */
    /*    */MenuNode category = entry(CHANCE_DICT, 1, "category", simpleDictIndex("机会分类", ChanceCategory.class));

    /**
     * 机会来源
     *
     * <p lang="en">
     * Source Type
     */
    /*    */MenuNode sourceType = entry(CHANCE_DICT, 2, "sourceType", simpleDictIndex("销售机会来源", ChanceSourceType.class));

    /**
     * 洽谈类型
     *
     * <p lang="en">
     * Action Style
     */
    /*    */MenuNode actionStyle = entry(CHANCE_DICT, 3, "actionStyle", simpleDictIndex("洽谈类型", ChanceActionStyle.class));

    /**
     * 供货方式
     *
     * <p lang="en">
     */
    /*    */MenuNode procurementMethod = entry(CHANCE_DICT, 5, "procurementMethod",
            simpleDictIndex("供货方式", ProcurementMethod.class));

    /**
     * 采购原则
     *
     * <p lang="en">
     */
    /*    */MenuNode purchaseRegulation = entry(CHANCE_DICT, 6, "purchaseRegulation",
            simpleDictIndex("采购原则", PurchaseRegulation.class));

    /**
     * 机会阶段
     *
     * <p lang="en">
     * Chance Stage
     */
    /*    */MenuNode stage = entry(CHANCE_DICT, 6, "stage", prefix.join("stage/"));

    /**
     * 机会优先级
     *
     * <p lang="en">
     * Chance Priority
     */
    /*    */MenuNode priority = entry(CHANCE_DICT, 7, "priority", prefix.join("priority/"));

    /**
     * 销售机会
     *
     * <p lang="en">
     * Sales Chance
     */
    MenuNode chance = entry(SALEMGMT, 10, "chance", prefix.join("chance/"));

    /**
     * 行动日志
     *
     * <p lang="en">
     * Chance Action
     */
    MenuNode action = entry(SALEMGMT, 11, "action", prefix.join("action/"));

    /**
     * 竞争对手管理
     *
     * <p lang="en">
     * Competitor
     */
    // MenuNode competitor = entry(CHANCE, "competitor", CHANCE_.join("competitor/ "));

}
