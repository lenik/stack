package com.bee32.sems.bom.web.zk.model;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.AbstractListModel;

import com.bee32.sems.bom.dto.ComponentDTO;
import com.bee32.sems.bom.service.BomServiceFacade;


public class ComponentListModel extends AbstractListModel {

    private static final long serialVersionUID = 1L;

    protected List<ComponentDTO> _items = new ArrayList<ComponentDTO>();

    public ComponentListModel(Long productId) {
        super();

        BomServiceFacade bomServiceFacade = (BomServiceFacade) SpringUtil.getBean("bomServiceFacade");
        _items = bomServiceFacade.listComponent(productId);
    }


    @Override
    public Object getElementAt(int index) {
        return _items.get(index);
    }

    @Override
    public int getSize() {
        return _items.size();
    }
}
