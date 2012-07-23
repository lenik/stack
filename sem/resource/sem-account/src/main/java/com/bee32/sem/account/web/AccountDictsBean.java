package com.bee32.sem.account.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.account.dto.BillTypeDto;
import com.bee32.sem.account.entity.BillType;

public class AccountDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<BillTypeDto> billTypes;


    public SelectableList<BillTypeDto> getBillTypes() {
        if (billTypes == null) {
            synchronized (this) {
                if (billTypes == null) {
                    billTypes = mrefList(BillType.class, BillTypeDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(billTypes);
    }


}
