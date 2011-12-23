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

import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.process.verify.ForVerifyContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

/**
 * 由任一管理员审核策略。
 */
@Entity
@DiscriminatorValue("V1")
@ForVerifyContext(ISingleVerifier.class)
public class SingleVerifierPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    private Set<Principal> responsibles;

    public SingleVerifierPolicy() {
        this(new Principal[0]);
    }

    public SingleVerifierPolicy(Principal singleManager) {
        this(new Principal[] { singleManager });
    }

    public SingleVerifierPolicy(Principal... responsibles) {
        this(Arrays.asList(responsibles));
    }

    public SingleVerifierPolicy(Collection<? extends Principal> responsibles) {
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
    @JoinTable(name = "SingleVerifyPolicy", //
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
        ISingleVerifier sv = checkedCast(ISingleVerifier.class, context);
        User user = sv.getVerifier1();

        if (user == null)
            return UNKNOWN;

        if (!user.impliesOneOf(responsibles))
            return VerifyResult.invalid(user);

        return VERIFIED;
    }

    @Override
    public VerifyResult evaluate(IVerifyContext context) {
        ISingleVerifier sv = checkedCast(ISingleVerifier.class, context);

        if (sv.getVerifier1() == null)
            return UNKNOWN;

        if (!sv.isAccepted1())
            return VerifyResult.rejected(sv.getVerifier1(), sv.getRejectedReason1());

        return VERIFIED;
    }

}
