package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;

/**
 * 由任一管理员审核策略。
 */
public class AllowList
        extends AbstractVerifyPolicy<Object, AllowState>
        implements IEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private Integer id;
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

    @Override
    public Integer getPrimaryKey() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<IPrincipal> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(Collection<IPrincipal> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    protected int hashCodeSpecific() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((responsibles == null) ? 0 : responsibles.hashCode());
        return result;
    }

    @Override
    protected boolean equalsSpecific(Component obj) {
        AllowList other = (AllowList) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (responsibles == null) {
            if (other.responsibles != null)
                return false;
        } else if (!responsibles.equals(other.responsibles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Allow-List: ");
        if (responsibles != null)
            for (IPrincipal responsible : responsibles) {
                buf.append('\n');
                buf.append(responsible);
            }
        return buf.toString();
    }

}
