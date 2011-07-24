package com.bee32.sem.process.verify.builtin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.Alias;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

/**
 * 由任一管理员审核策略。
 */
@Entity
@DiscriminatorValue("LS")
@Alias("list")
public class AllowList
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    private Set<Principal> responsibles;

    public AllowList() {
        this(new Principal[0]);
    }

    public AllowList(Principal singleManager) {
        this(new Principal[] { singleManager });
    }

    public AllowList(Principal... responsibles) {
        this(Arrays.asList(responsibles));
    }

    public AllowList(Collection<? extends Principal> responsibles) {
        super(IAllowedByContext.class);
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = new HashSet<Principal>(responsibles);
    }

    /**
     * Allow-List 为静态责任人列别，从不考虑实体类定义的额外名单。 故 context 参数被忽略。
     */
    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifyContext context) {
        return getResponsibles();
    }

    @ManyToMany
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
    public VerifyResult validate(IVerifyContext context) {
        IAllowedByContext allowedBy = requireContext(IAllowedByContext.class, context);
        User user = allowedBy.getVerifier();

        if (user == null)
            return UNKNOWN;

        if (!user.impliesOneOf(responsibles))
            return VerifyResult.invalid(user);

        return VERIFIED;
    }

    @Override
    public VerifyResult evaluate(IVerifyContext context) {
        IAllowedByContext allowedBy = requireContext(IAllowedByContext.class, context);

        if (allowedBy.getVerifier() == null)
            return UNKNOWN;

        if (!allowedBy.isAllowed())
            return VerifyResult.rejected(allowedBy.getVerifier(), allowedBy.getRejectedReason());

        return VERIFIED;
    }

}
