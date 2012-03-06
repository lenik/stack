package com.bee32.sem.process.verify.builtin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.bee32.icsf.principal.Principal;
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

    public static final int VERIFYING_1 = 1;
    public static final int END = -1;
    static final Set<Integer> predefinedStages = setOf(VERIFYING_1, END);

    private Set<Principal> responsibles = new HashSet<Principal>();

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

    @Override
    public void populate(Object source) {
        if (source instanceof SingleVerifierPolicy)
            _populate((SingleVerifierPolicy) source);
        else
            super.populate(source);
    }

    protected void _populate(SingleVerifierPolicy o) {
        super._populate(o);
        responsibles = new HashSet<Principal>(o.responsibles);
    }

    @Transient
    @Override
    public Set<?> getPredefinedStages() {
        return predefinedStages;
    }

    @Override
    public Object getStage(IVerifyContext context) {
        ISingleVerifier sv = checkedCast(ISingleVerifier.class, context);
        if (sv.getVerifier1() == null)
            return VERIFYING_1;
        else
            return END;
    }

    /**
     * Allow-List 为静态责任人列别，从不考虑实体类定义的额外名单。 故 context 参数被忽略。
     */
    @Override
    public Set<Principal> getStageResponsibles(Object stage) { //
        if (stage.equals(VERIFYING_1))
            return responsibles;
        else
            return Collections.emptySet();
    }

    @ManyToMany
    @JoinTable(name = "SingleVerifyPolicy", //
    /*        */joinColumns = @JoinColumn(name = "allowList"), //
    /*        */inverseJoinColumns = @JoinColumn(name = "responsible"))
    public Set<Principal> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(Set<Principal> responsibles) {
        if (responsibles == null)
            throw new NullPointerException("responsibles");
        this.responsibles = responsibles;
    }

    public void addResponsible(Principal responsible) {
        responsibles.add(responsible);
    }

    public void removeResponsible(Principal responsible) {
        responsibles.remove(responsible);
    }

    @Override
    public VerifyResult validate(IVerifyContext context) {
        ISingleVerifier sv = checkedCast(ISingleVerifier.class, context);
        Principal user = sv.getVerifier1();

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
