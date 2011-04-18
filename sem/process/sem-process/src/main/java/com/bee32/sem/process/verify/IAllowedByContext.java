package com.bee32.sem.process.verify;

import com.bee32.icsf.principal.User;

/**
 * Used by single man verify policies.
 */
public interface IAllowedByContext
        extends IVerifyContext {

    boolean isAllowed();

    void setAllowed(boolean isAllowed);

    User getUser();

    void setUser(User user);

    String getMessage();

    void setMessage(String message);

}
