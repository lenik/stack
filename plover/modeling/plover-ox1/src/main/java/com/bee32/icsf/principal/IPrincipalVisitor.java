package com.bee32.icsf.principal;

public interface IPrincipalVisitor {

    /**
     * @return <code>true</code> to visit more implications implied by <code>principal</code> , or
     *         <code>false</code> to skip.
     */
    boolean startPrincipal(IPrincipal principal);

    void endPrincipal(IPrincipal principal);

    void visitImplication(IPrincipal impliedPrincipal);

}
