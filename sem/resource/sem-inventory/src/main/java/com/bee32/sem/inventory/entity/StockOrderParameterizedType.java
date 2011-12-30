package com.bee32.sem.inventory.entity;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.ModuleLoader;
import com.bee32.plover.arch.generic.AbstractParameterizedType;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.IObjectURLFragmentsProvider;
import com.bee32.plover.restful.resource.ModuleObjectPageDirectory;
import com.bee32.plover.restful.resource.ObjectURLFragmentType;
import com.bee32.sem.inventory.SEMInventoryModule;

public class StockOrderParameterizedType
        extends AbstractParameterizedType
        implements IObjectURLFragmentsProvider {

    public StockOrderParameterizedType() {
        super(StockOrder.class);
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

    static Map<StockOrderSubject, String> moduleHrefMap = new HashMap<StockOrderSubject, String>();
    {
        moduleHrefMap.put(StockOrderSubject.INIT, "init/");
        moduleHrefMap.put(StockOrderSubject.TAKE_IN, "take/");
        moduleHrefMap.put(StockOrderSubject.TAKE_OUT, "take/");
        moduleHrefMap.put(StockOrderSubject.FACTORY_IN, "take/");
        moduleHrefMap.put(StockOrderSubject.FACTORY_OUT, "take/");
        moduleHrefMap.put(StockOrderSubject.STKD, "stocktaking/");
        moduleHrefMap.put(StockOrderSubject.XFER_IN, "transferIn/");
        moduleHrefMap.put(StockOrderSubject.XFER_OUT, "transferOut/");
        moduleHrefMap.put(StockOrderSubject.OSP_IN, "outsourcingIn/");
        moduleHrefMap.put(StockOrderSubject.OSP_OUT, "outsourcingOut/");
    }

    /**
     *
     */
    @Override
    public String getURLFragments(Object instance, ObjectURLFragmentType fragmentType) {
        StockOrder o = (StockOrder) instance;
        StockOrderSubject subject = o.getSubject();
        switch (fragmentType) {
        case baseHrefToModule:
            return moduleHrefMap.get(subject);
        case extraParameters:
            return "subject=" + subject.getValue();
        }
        throw new IllegalArgumentException("Bad fragment type: " + fragmentType);
    }

    class Delegate
            extends ModuleObjectPageDirectory {

        public Delegate() {
            super(ModuleLoader.getInstance().getModule(SEMInventoryModule.class));
        }

    }

    @Override
    public <T> T getFeature(Object instance, Class<T> featureClass) {
        if (featureClass.equals(IObjectPageDirectory.class))
            return delegate;
        return super.getFeature(instance, featureClass);
    }

    public static final StockOrderParameterizedType INSTANCE = new StockOrderParameterizedType();

}
