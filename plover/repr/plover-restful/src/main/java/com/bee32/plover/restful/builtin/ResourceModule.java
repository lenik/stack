package com.bee32.plover.restful.builtin;

import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.credit.CreditSubject;
import com.bee32.plover.arch.credit.builtin.Lenik;
import com.bee32.plover.disp.DispatchModule;
import com.bee32.plover.disp.IDispatcher;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;
import com.bee32.plover.restful.resource.ResourceDispatcher;


@Oid({ 3, 12, PloverOids.repr, PloverOids.reprResource })
public class ResourceModule
        extends DispatchModule {

    public ResourceModule() {
        super();
    }

    public ResourceModule(String name) {
        super(name);
    }

    @Override
    public IDispatcher getDispatcher() {
        return ResourceDispatcher.getInstance();
    }

    @Override
    public Credit getCredit() {
        return CREDIT;
    }

    static Credit CREDIT;
    {
        CREDIT = new Credit();
        CREDIT.addContributor(CreditSubject.SystemArchitect, Lenik.getLenik());
    }

}
