package com.bee32.sems.bom.web.zk.model;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import com.bee32.plover.zk.model.AbstractPagingListModel;
import com.bee32.sems.bom.dto.ProductDTO;
import com.bee32.sems.bom.service.BomServiceFacade;

public class ProductPagingListModel extends AbstractPagingListModel<ProductDTO> {

    public ProductPagingListModel(int startPageNumber, int pageSize) {
        super(startPageNumber, pageSize);
    }

    /**
     *
     */
    private static final long serialVersionUID = -7270654283002532294L;

    @Override
    public long getTotalSize() {
        BomServiceFacade bomServiceFacade = (BomServiceFacade)SpringUtil.getBean("bomServiceFacade");
        return bomServiceFacade.productCount();
    }

    @Override
    protected List<ProductDTO> getPageData(int itemStartNumber, int pageSize) {
        BomServiceFacade bomServiceFacade = (BomServiceFacade)SpringUtil.getBean("bomServiceFacade");
        return bomServiceFacade.listProduct(itemStartNumber, pageSize);
    }

}
