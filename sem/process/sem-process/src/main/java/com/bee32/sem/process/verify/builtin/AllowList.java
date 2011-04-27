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
import javax.persistence.ManyToMany;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalBeanConfig;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.util.Alias;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.VerifyPolicy;

/**
 * 由任一管理员审核策略。
 */
@Entity
@DiscriminatorValue("LS")
@Alias("list")
public class AllowList
        extends VerifyPolicy<IAllowedByContext> {

    private static final long serialVersionUID = 1L;

    private Set<Principal> responsibles;

    public AllowList() {
    }

    public AllowList(Principal singleManager) {
        addResponsible(singleManager);
    }

    public AllowList(Principal... responsibles) {
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = new HashSet<Principal>(Arrays.asList(responsibles));
    }

    public AllowList(Collection<? extends Principal> responsibles) {
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = new HashSet<Principal>(responsibles);
    }

    /**
     * Allow-List 为静态责任人列别，从不考虑实体类定义的额外名单。 故 context 参数被忽略。
     */
    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(IAllowedByContext context) {
        return getResponsibles();
    }

    @ManyToMany(targetEntity = Principal.class, fetch = FetchType.EAGER)
    @JoinTable(name = "AllowList", //
    /*        */joinColumns = @JoinColumn(name = "allowList"), //
    /*        */inverseJoinColumns = @JoinColumn(name = "responsible"))
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
    public VerifyResult validate(IAllowedByContext context) {
        User user = context.getVerifier();

        if (user == null)
            return UNKNOWN;

        if (!user.impliesOneOf(responsibles))
            return VerifyResult.invalid(user);

        return null;
    }

    @Override
    public VerifyResult evaluate(IAllowedByContext context) {
        if (context.getVerifier() == null)
            return UNKNOWN;

        if (!context.isAllowed())
            return VerifyResult.rejected(context.getVerifier(), context.getRejectReason());

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
