package com.bee32.icsf.principal;

import javax.free.IdentityHashSet;

/**
 * @test {@link com.bee32.icsf.principal.util.icsf.principal.util.PrincipalDiagTest}
 */
public class PrincipalDiag {

    public static int maxRefDepth = 16;

    /**
     * 检查安全主体中是否存在引用回路。
     *
     * @throws PrincipalCheckException
     *             当存在引用回路。
     */
    public static void checkDeadLoop(final IPrincipal start)
            throws PrincipalCheckException {
        /*
         * optimized result cache, for implied principals who don't make a loop to the start,
         * there's no need search them again.
         *
         * spec ⇒ base (specialized ⇒ common-base)
         */
        final IdentityHashSet ident = new IdentityHashSet();

        try {
            start.accept(new IPrincipalVisitor() {

                /**
                 * A root-only node has depth of 1.
                 */
                int depth = 0;

                @Override
                public boolean startPrincipal(IPrincipal principal) {
                    if (depth >= maxRefDepth)
                        throw new PrincipalDiagException("Implication too deep: " + principal);
                    depth++;
                    return true;
                }

                @Override
                public void endPrincipal(IPrincipal principal) {
                    assert --depth >= 0;
                }

                @Override
                public void visitImplication(IPrincipal impliedSpec) {
                    if (impliedSpec.implies(start))
                        throw new PrincipalDiagException("Loop detected: " + impliedSpec);

                    if (ident.add(impliedSpec)) {
                        impliedSpec.accept(this);
                    }
                }

            });
        } catch (PrincipalDiagException e) {
            throw new PrincipalCheckException(e.getMessage(), e);
        }
    }

}
