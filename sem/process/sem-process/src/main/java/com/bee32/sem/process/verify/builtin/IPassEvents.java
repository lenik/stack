package com.bee32.sem.process.verify.builtin;

import com.bee32.icsf.principal.User;
import com.bee32.sem.process.verify.IVerifyContext;

interface IPassEvents
        extends IVerifyContext {

    /**
     * Get the position in the pass-to-next policy.
     */
    int getPosition();

    void setPosition(int position);

    /**
     * Add a passed-by event entry.
     */
    void passBy(User user);

    /**
     * Add a rejected-by event entry
     */
    void rejectBy(User user);

    /**
     * Number of log entries.
     */
    int size();

    /**
     * Get event entry of specified index.
     *
     * @throws IndexOutOfBoundsException
     */
    VerifyEvent getEvent(int index);

    void removeEvent(int index);

    void clear();

}
