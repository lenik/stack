package com.bee32.plover.inject.scope;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.test.WiredTestCase;

public class StateScopeTest
        extends WiredTestCase {

    static StateManager manager = StateManager.getGlobalStateManager();

    /**
     * When proxy is used, a void context is useless.
     */
    @BeforeClass
    public static void setupVoidContext() {
        manager.setCurrentState("void");
    }

    @Inject
    ApplicationContext app;

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
        StateHint hint = app.getBean(StateHint.class);
        hint.setHint("a-hint");

        manager.setCurrentState("b");
        hint.setHint("b-hint");

        manager.setCurrentState("a");
        assertEquals("a-hint", hint.getHint());
    }

    @Test
    public void testScopeRefetch() {
        manager.setCurrentState("a");
        StateHint a = app.getBean(StateHint.class);
        a.setHint("a-hint");

        manager.setCurrentState("b");
        StateHint b = app.getBean(StateHint.class);
        b.setHint("b-hint");

        manager.setCurrentState("a");
        StateHint a2 = app.getBean(StateHint.class);
        assertEquals("a-hint", a2.getHint());
    }

    /**
     * Result: Interface proxy is slightly faster.
     * <ul>
     * <li>NONE/1: 301513, 435516
     * <li>CLASS/1: 43126, 44792, 51576, 52630, 22733
     * <li>IFACE/1: 118927, 284336, 222006
     * <li>NONE/5: 3249465, 2737048, 2711960
     * <li>CLASS/5: 2268238, 2261569, 2220071
     * <li>IFACE/5: 2316550, 1979359, 2353264, 2346242
     * </ul>
     */
    // @Test
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
