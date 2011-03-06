package com.bee32.plover.arch.naming;

import java.util.Collection;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.arch.operation.IOperation;

public interface INamedNode
        extends IComponent {

    /**
     * The priority is used for reversed object lookup.
     *
     * @return The less number the higher priority is.
     */
    int getPriority();

    /**
     * Get the parent node.
     *
     * @return <code>null</code> if this node doesn't have a parent.
     */
    INamedNode getParent();

    /**
     * Get the base type for the immediate children nodes.
     *
     * @return Non-<code>null</code> type of the children nodes.
     */
    Class<?> getChildType();

    /**
     * Get managed node with specific name.
     *
     * @param childName
     *            A location component.
     * @return Non-<code>null</code> object located. Returns <code>null</code> if the location isn't
     *         recognized, and the next locator for the same type should be checked.
     */
    Object getChild(String childName);

    /**
     * Check if the child object is managed by this node.
     *
     * @param obj
     *            child object to be checked
     * @return <code>true</code> if the child object is owned by this node.
     */
    boolean hasChild(Object obj);

    /**
     * Get the URI location component for the object.
     *
     * @param obj
     *            Non-<code>null</code> object.
     * @return Location string. Returns <code>null</code> if specified <code>obj</code> is not owned
     *         by this locator, and other locators for the same type should be checked.
     * @see ReverseLookupRegistry#getLocatorForObject(Object)
     */
    String getChildName(Object obj);

    /**
     * Get all children's path names.
     *
     * @return Non-<code>null</code> string collection of child names.
     */
    Collection<String> getChildNames();

    /**
     * Get operation with a specific name.
     *
     * @return <code>null</code> if the operation with specified name doesn't exist.
     */
    IOperation getOperation(String name);

    /**
     * Get all operations.
     *
     * @return Non-<code>null</code> collection of operation objects.
     */
    Collection<IOperation> getOperations();

}