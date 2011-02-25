package com.bee32.plover.model.qualifier;

/**
 * The standard qualifier type priority.
 *
 * @see Priority#getQualifierPriority()
 */
public interface StdPriority {

    int priority = Integer.MIN_VALUE;

    int profile = 1;
    int group = 2;

    int position = 10;

}
