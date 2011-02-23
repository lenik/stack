package com.bee32.sem.process.verify;

import com.bee32.plover.model.Model;

public abstract class AbstractVerifyPolicy
        extends Model
        implements IVerifyPolicy {

    private static final long serialVersionUID = 1L;

    /**
     * Initialize the policy name from the class name.
     */
    public AbstractVerifyPolicy() {
    }

    /**
     * @param policyName
     *            Non-<code>null</code> name of policy.
     */
    public AbstractVerifyPolicy(String policyName) {
        super(policyName);
    }

    public boolean isVerified(IVerifiable verifyObject)
            throws BadVerifyDataException {
        try {
            verify(verifyObject);
            return true;
        } catch (BadVerifyDataException e) {
            throw e;
        } catch (VerifyException e) {
            return false;
        }
    }

}
