package com.bee32.sem.world.monetary;

import java.io.IOException;
import java.util.Map;

import javax.free.UnexpectedException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.GlobalAppCtx;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.world.monetary.impl.DiscreteFxrProvider;
import com.bee32.sem.world.monetary.impl.FxrSamplesSource;

public class FxrProviderFactory {

    static IFxrProvider fxrProvider;

    static ApplicationContext getApplicationContext() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();
        if (context == null)
            context = GlobalAppCtx.getApplicationContext();
        return context;
    }

    /**
     * 获取外汇查询服务，该服务用于计算本地货币表示的价格和本地货币表示的金额。
     */
    public static IFxrProvider getFxrProvider() {
        if (fxrProvider == null) {
            synchronized (FxrProviderFactory.class) {
                if (fxrProvider == null) {
                    ApplicationContext appctx = getApplicationContext();
                    if (appctx == null) {
                        fxrProvider = new DiscreteFxrProvider();
                        injectSamples(fxrProvider);
                    } else {
                        Map<String, IFxrProvider> beans = appctx.getBeansOfType(IFxrProvider.class);
                        if (beans.isEmpty())
                            throw new IllegalStateException("No available FXP Provider.");
                        fxrProvider = beans.values().iterator().next();
                    }
                }
            }
        }
        return fxrProvider;
    }

    /**
     * 调度器上的 SampleSource/Job 和这里的Eagerly initialized FXP 在同时注入的时候， 在 {@link DiscreteFxrProvider}
     * 中会根据数据库中的 latestCommitDate 来避免重复。
     */
    static void injectSamples(IFxrProvider fxp) {
        FxrSamplesSource testSource = new FxrSamplesSource();
        try {
            for (int i = 0; i < testSource.getCount(); i++) {
                FxrTable table = testSource.download();
                assert table != null;
                fxp.commit(table);
            }
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }

}
