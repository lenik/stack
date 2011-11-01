package com.bee32.plover.inject.scope;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;

public class StateScopeTest
        extends WiredTestCase {

    static StateManager manager = StateManager.getGlobalStateManager();

    /**
     * When proxy is used, a void context is useless.
     */
    // @BeforeClass
    public static void setupVoidContext() {
        manager.setCurrentState("void");
    }

    @Inject
    IStateHint hint;

    /**
     * 本测试的目的：
     * <ol>
     * <li>state-scoped hint 在无 proxy 时，inject 将失败，因为 state context 尚不存在。
     * <li>proxy 之后，state context 在第一次 bean method 时即被惰性创建。
     * </ol>
     *
     * 同时，测试显示出：
     */
    @Test
    public void testScopeProxy() {
        manager.setCurrentState("a");
        hint.setHint("a-hint");

        manager.setCurrentState("b");
        hint.setHint("b-hint");

        manager.setCurrentState("a");
        assertEquals("a-hint", hint.getHint());
    }

    /**
     * Result: Interface proxy is slightly faster.
     * <ul>
     * <li>CLASS/1: 43126, 44792, 51576, 52630, 22733
     * <li>IFACE/1: 118927, 284336, 222006
     * <li>CLASS/5: 2268238, 2261569, 2220071
     * <li>IFACE/5: 2316550, 1979359, 2353264, 2346242
     * </ul>
     */
    @Test
    public void testProxyPerformance() {
        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start < 1000) { // delay for 1sec
            hint.setHint("hello");
            count++;
        }
        System.out.println("Count: " + count);
    }

}
