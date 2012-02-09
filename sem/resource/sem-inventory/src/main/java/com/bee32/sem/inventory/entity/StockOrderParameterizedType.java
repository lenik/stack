package com.bee32.sem.inventory.entity;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.orm.util.ParameterizedEntityType;
import com.bee32.plover.restful.resource.ObjectURLFragmentType;
import com.bee32.sem.inventory.SEMInventoryModule;

public class StockOrderParameterizedType
        extends ParameterizedEntityType {

    public StockOrderParameterizedType() {
        super(SEMInventoryModule.class, StockOrder.class);
    }

    @Override
    public String getDisplayTypeName(Object instance) {
        StockOrder o = (StockOrder) instance;
        String subjectName = o.getSubject().getDisplayName();
        return subjectName + "单"; // TODO - NLS '单'、'order'
    }

    @Override
    protected void populateParameterMap(Object instance, Map<String, Object> parameterMap) {
        StockOrder o = (StockOrder) instance;
        StockOrderSubject subject = o.getSubject();
        parameterMap.put("subject", subject);
    }

    static Map<StockOrderSubject, String> subjectHrefMap = new HashMap<StockOrderSubject, String>();
    {
        subjectHrefMap.put(StockOrderSubject.INIT, "stock/INIT/");
        subjectHrefMap.put(StockOrderSubject.TAKE_IN, "stock/");
        subjectHrefMap.put(StockOrderSubject.TAKE_OUT, "stock/");
        subjectHrefMap.put(StockOrderSubject.FACTORY_IN, "stock/");
        subjectHrefMap.put(StockOrderSubject.FACTORY_OUT, "stock/");
        subjectHrefMap.put(StockOrderSubject.STKD, "stock/STKD/");
        subjectHrefMap.put(StockOrderSubject.XFER_IN, "stock/XFER_IN/");
        subjectHrefMap.put(StockOrderSubject.XFER_OUT, "stock/XFER_OUT/");
        subjectHrefMap.put(StockOrderSubject.OSP_IN, "stock/OSP_IN/");
        subjectHrefMap.put(StockOrderSubject.OSP_OUT, "stock/OSP_OUT/");
    }

    @Override
    public Object getURLFragment(Object instance, ObjectURLFragmentType fragmentType) {
        StockOrder o = (StockOrder) instance;
        StockOrderSubject subject = o.getSubject();
        switch (fragmentType) {
        case baseHrefToModule:
            return subjectHrefMap.get(subject);
        case extraParameters:
            Map<String, String> params = new HashMap<String, String>();
            params.put("subject", subject.getValue());
            return params;
        }
        throw new IllegalArgumentException("Bad fragment type: " + fragmentType);
    }

    public static final StockOrderParameterizedType INSTANCE = new StockOrderParameterizedType();

}
