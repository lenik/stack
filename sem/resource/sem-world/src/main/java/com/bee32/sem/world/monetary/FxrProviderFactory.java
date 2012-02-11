package com.bee32.sem.world.monetary;

import java.io.IOException;
import java.util.Map;

import javax.free.UnexpectedException;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.orm.util.BootstrapDataAssembledContext;
import com.bee32.sem.world.monetary.impl.DiscreteFxrProvider;
import com.bee32.sem.world.monetary.impl.FxrSamplesSource;

public class FxrProviderFactory {

    protected static class ctx
            extends BootstrapDataAssembledContext {
    }

    static IFxrProvider fxrProvider;

    /**
     * 获取外汇查询服务，该服务用于计算本地货币表示的价格和本地货币表示的金额。
     */
    public static IFxrProvider getFxrProvider() {
        if (fxrProvider == null) {
            synchronized (FxrProviderFactory.class) {
                if (fxrProvider == null) {
                    ApplicationContext appctx = ctx.bean.getAppCtx();
                    if (appctx == null) {
                        fxrProvider = new DiscreteFxrProvider();
                        injectSamples(fxrProvider);
                    } else {
                        Map<String, IFxrProvider> beans = ctx.bean.getBeansOfType(IFxrProvider.class);
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
