package com.bee32.icsf.access.shield;

public class AclOptionManager {

    ThreadLocal<AclOptionStack> stacks = new ThreadLocal<AclOptionStack>();

    public synchronized AclOptionStack getAclOptionStack() {
        AclOptionStack stack = stacks.get();
        if (stack == null) {
            stack = new AclOptionStack();
            stacks.set(stack);
        }
        return stack;
    }

    static final AclOptionManager instance = new AclOptionManager();

    public static AclOptionManager getInstance() {
        return instance;
    }

    public static AclFrameHandler newAclDisabler() {
        AclOptionStack aclOptionStack = AclOptionManager.getInstance().getAclOptionStack();
        AclOption aclOption = aclOptionStack.enter();
        aclOption.setEnabled(false);
        return new AclFrameHandler(aclOptionStack);
    }

}
