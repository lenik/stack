package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;

/**
 * 由任一管理员审核策略。
 */
public class AllowList
        extends AbstractVerifyPolicy<Object, AllowState>
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private Collection<IPrincipal> responsibles;

    public AllowList() {
        super(AllowState.class);
        this.responsibles = new ArrayList<IPrincipal>();
    }

    public AllowList(IPrincipal singleManager) {
        super(AllowState.class);
        this.responsibles = new ArrayList<IPrincipal>(1);
        this.responsibles.add(singleManager);
    }

    public AllowList(IPrincipal... responsibles) {
        super(AllowState.class);
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = Arrays.asList(responsibles);
    }

    public AllowList(Collection<IPrincipal> responsibles) {
        super(AllowState.class);
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = responsibles;
    }

    @Override
    public String checkState(Object context, AllowState state) {
        if (state == null)
            return "尚未审核。";

        IPrincipal principal = state.getPrincipal();

        if (!responsibles.contains(principal))
            return "无效的审核人：" + principal;

        return null;
    }

    public Collection<IPrincipal> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(Collection<IPrincipal> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        out.print("Allow-List: ");
        if (responsibles != null)
            for (IPrincipal responsible : responsibles) {
                out.print('\n');
                out.print(responsible);
            }
    }

}
