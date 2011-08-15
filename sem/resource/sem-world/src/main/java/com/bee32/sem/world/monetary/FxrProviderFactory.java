package com.bee32.sem.world.monetary;

import java.io.IOException;
import java.util.Map;

import javax.free.UnexpectedException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.world.monetary.impl.DiscreteFxrProvider;
import com.bee32.sem.world.monetary.impl.FxrSamplesSource;

public class FxrProviderFactory {

    static ApplicationContext getApplicationContext() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();
        return context;
    }

    /**
     * 获取外汇查询服务，该服务用于计算本地货币表示的价格和本地货币表示的金额。
     */
    public static IFxrProvider getFxrProvider() {
        ApplicationContext appctx = getApplicationContext();

        if (appctx == null)
            return getTestFxrProvider();

        Map<String, IFxrProvider> beans = appctx.getBeansOfType(IFxrProvider.class);
        if (beans.isEmpty())
            throw new IllegalStateException("No available FXP Provider.");

        IFxrProvider first = beans.values().iterator().next();
        return first;
    }

    static IFxrProvider testFxrProvider;

    static IFxrProvider getTestFxrProvider() {
        if (testFxrProvider == null) {
            FxrSamplesSource testSource = new FxrSamplesSource();
            DiscreteFxrProvider fxp = new DiscreteFxrProvider();

            try {
                for (int i = 0; i < testSource.getCount(); i++) {
                    FxrTable table = testSource.download();
                    assert table != null;
                    fxp.commit(table);
                }
            } catch (IOException e) {
                throw new UnexpectedException(e);
            }

            testFxrProvider = fxp;
        }
        return testFxrProvider;
    }

}
