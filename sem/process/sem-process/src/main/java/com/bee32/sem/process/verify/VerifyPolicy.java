package com.bee32.sem.process.verify;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.icsf.principal.IPrincipalChangeListener;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalAware;
import com.bee32.icsf.principal.PrincipalChangeEvent;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.ox1.color.UIEntityAuto;

@ServiceTemplate(prototype = true)
@Entity(name = "VerifyPolicy")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "verify_policy_seq", allocationSize = 1)
public abstract class VerifyPolicy
        extends UIEntityAuto<Integer>
        implements IVerifyPolicy, IPrincipalChangeListener {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 10;

    // transient.
    private Map<Principal, Map<Object, Boolean>> principalResponsibleStagesMap;

    public VerifyPolicy() {
    }

    @Override
    public void populate(Object source) {
        if (source instanceof VerifyPolicy)
            _populate((VerifyPolicy) source);
        else
            super.populate(source);
    }

    protected void _populate(VerifyPolicy o) {
        super._populate(o);
        // principalResponsibleStagesMap=new HashMap<Principal,
        // Map<Object,Boolean>>(o.principalResponsibleStagesMap);
    }

    @Transient
    @Override
    public VerifyPolicyMetadata getMetadata() {
        return VerifyPolicyManager.getMetadata(getClass());
    }

    @Override
    public final void assertVerified(IVerifyContext context)
            throws VerifyException {
        VerifyResult errorResult = verify(context);
        if (!errorResult.isVerified())
            throw new VerifyException(String.valueOf(errorResult));
    }

    @Override
    public final boolean isVerified(IVerifyContext context) {
        VerifyResult result = verify(context);
        return result.isVerified();
    }

    @Override
    public VerifyResult verify(IVerifyContext context) {

        // policy-consistency validation.
        VerifyResult result = validate(context);

        // Continue to check if the state is validated.
        if (result.isVerified())
            result = evaluate(context);

        return result;
    }

    /**
     * 检查审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return {@link #VERIFIED} means verified, otherwise the error message.
     */
    public VerifyResult validate(IVerifyContext context) {
        return VERIFIED;
    }

    /**
     * 计算审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return {@link #VERIFIED} means verified, otherwise the error message.
     */
    public abstract VerifyResult evaluate(IVerifyContext context);

    @Override
    public Set<Principal> getResponsibles(IVerifyContext context) {
        Object stage = getStage(context);
        return getStageResponsibles(stage);
    }

    @Override
    protected void finalize() {
        if (principalResponsibleStagesMap != null)
            PrincipalAware.getInstance().removePrincipalChangeListener(this);
    }

    protected Map<Object, Boolean> getResponsibleStagesMap(Principal principal) {
        if (principalResponsibleStagesMap == null) {
            synchronized (this) {
                if (principalResponsibleStagesMap == null) {
                    principalResponsibleStagesMap = new HashMap<Principal, Map<Object, Boolean>>();
                    PrincipalAware.getInstance().addPrincipalChangeListener(this);
                }
            }
        }
        Map<Object, Boolean> responsibleStagesMap = principalResponsibleStagesMap.get(principal);
        if (responsibleStagesMap == null) {
            synchronized (principalResponsibleStagesMap) {
                if (responsibleStagesMap == null) {
                    responsibleStagesMap = new HashMap<Object, Boolean>();
                    principalResponsibleStagesMap.put(principal, responsibleStagesMap);
                }
            }
        }
        return responsibleStagesMap;
    }

    @Override
    public boolean isResponsible(Principal principal, Object stage) {
        Map<Object, Boolean> responsibleStages = getResponsibleStagesMap(principal);
        Boolean responsible = responsibleStages.get(stage);
        if (responsible == null) {
            synchronized (responsibleStages) {
                if (responsible == null) {
                    for (Principal im : principal.getImSet())
                        if (getStageResponsibles(stage).contains(im)) {
                            responsible = true;
                            break;
                        }
                    if (responsible == null)
                        responsible = false;
                    responsibleStages.put(stage, responsible);
                }
            }
        }
        return responsible;
    }

    @Override
    public void principalChanged(PrincipalChangeEvent event) {
        if (principalResponsibleStagesMap != null) {
            Principal principal = event.getPrincipal();
            principalResponsibleStagesMap.remove(principal);
        }
    }

    protected static <C extends IVerifyContext> C checkedCast(Class<C> requiredContextClass, IVerifyContext context) {
        if (requiredContextClass == null)
            throw new NullPointerException("requiredContextClass");
        /**
         * <pre>
         * if (requiredContextClass.equals(metadata.getContextClass()))
         *     throw new IllegalUsageException(&quot;Require a different context from the defined one: &quot; + requiredContextClass);
         * </pre>
         */
        return requiredContextClass.cast(context);
    }

    @SafeVarargs
    protected static <T> Set<T> setOf(T... args) {
        Set<T> set = new LinkedHashSet<T>();
        for (T arg : args)
            set.add(arg);
        return set;
    }

}
