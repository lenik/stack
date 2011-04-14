package com.bee32.sem.process.verify.builtin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalBeanConfig;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.result.ErrorResult;
import com.bee32.sem.process.verify.result.RejectedResult;
import com.bee32.sem.process.verify.result.UnauthorizedResult;

/**
 * 由任一管理员审核策略。
 */
@Entity
@DiscriminatorValue("LS")
public class AllowList
        extends VerifyPolicy<Object, AllowState> {

    private static final long serialVersionUID = 1L;

    private Set<Principal> responsibles;

    public AllowList() {
        super(AllowState.class);
    }

    public AllowList(Principal singleManager) {
        super(AllowState.class);
        addResponsible(singleManager);
    }

    public AllowList(Principal... responsibles) {
        super(AllowState.class);
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = new HashSet<Principal>(Arrays.asList(responsibles));
    }

    public AllowList(Collection<? extends Principal> responsibles) {
        super(AllowState.class);
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = new HashSet<Principal>(responsibles);
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(Object context) {
        return getResponsibles();
    }

    @OneToMany(targetEntity = Principal.class, fetch = FetchType.EAGER)
    @JoinTable(name = "AllowListResponsible", //
    /*        */joinColumns = @JoinColumn(name = "responsible"), //
    /*        */inverseJoinColumns = @JoinColumn(name = "allowList"))
    public Set<Principal> getResponsibles() {
        if (responsibles == null) {
            synchronized (this) {
                if (responsibles == null) {
                    responsibles = new HashSet<Principal>();
                }
            }
        }
        return responsibles;
    }

    public void setResponsibles(Set<Principal> responsibles) {
        this.responsibles = responsibles;
    }

    public void addResponsible(Principal responsible) {
        getResponsibles().add(responsible);
    }

    public void removeResponsible(Principal responsible) {
        getResponsibles().remove(responsible);
    }

    @Override
    public ErrorResult validateState(Object context, AllowState state) {
        User user = state.getUser();

        if (!user.impliesOneOf(responsibles))
            return new UnauthorizedResult(user);

        return null;
    }

    @Override
    public ErrorResult checkState(Object context, AllowState state) {
        if (!state.isAllowed())
            return new RejectedResult(state.getUser(), state.getMessage());

        return null;
    }

    @Override
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((responsibles == null) ? 0 : responsibles.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> otherEntity) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        AllowList other = (AllowList) otherEntity;

        if (!Nullables.equals(responsibles, other.responsibles))
            return false;

        return true;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        out.print("Allow-List: ");
        if (responsibles != null)
            for (Principal responsible : responsibles) {
                out.print('\n');
                out.print(responsible.getName());
            }
    }

}
