package com.bee32.sem.bom.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.faces.utils.FacesContextUtils;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.entity.PartItem;
import com.bee32.sem.inventory.entity.Material;

public class PartService
        extends DataService {

    /**
     * 批量更改PartItem中的material为part,即把PartItem从原材料改为半成品
     */
    @Transactional
    public void changePartItemFromMaterialToPart(Part part) {
        FacesUILogger uiLogger = FacesContextUtils.getUILogger();

        // 保存前查找partItem为原材料的物料和当前part的target是否相同，
        // 如果相同，则把这些partItem中的material设为null,part设为当前part
        // 进行这项操作是为解决不能正向设置bom的问题
        Material material = part.getTarget();
        if (material == null) {
            uiLogger.error("组件没有设置对应的物料");
            return;
        }

        List<PartItem> items = asFor(PartItem.class).list(new Equals("material.id", material.getId()));

        if (items != null && items.size() > 0) {
            for (PartItem item : items) {
                item.setMaterial(null);
                item.setPart(part);
                asFor(PartItem.class).saveOrUpdate(item);
            }
        }
    }

}
