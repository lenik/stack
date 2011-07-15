package com.bee32.plover.orm.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;

/**
 * Lifecycle:
 *
 * <ol>
 * <li>Spring scan all exported contribs
 * <li>Add dependency to virtual packages within ctor.
 * <li>ImportSamplesUtil add dependencies later
 * <li>Should virtualpackage.XXX implied all specific samples.
 * </ol>
 */
@ComponentTemplate
@Lazy
public abstract class SampleContribution
        extends SamplePackage {

    static Logger logger = LoggerFactory.getLogger(SampleContribution.class);

    public static final String LOADED_SAMPLE_PACKAGE_KEY = "sample";

    private boolean assembled;

    public SampleContribution() {
        this(VirtualSamplePackage.NORMAL);
    }

    public SampleContribution(String name) {
        super(name);
    }

    protected SampleContribution(SamplePackage... outers) {
        for (SamplePackage outer : outers)
            addTargetPackage(outer);
    }

    protected void addTargetPackage(SamplePackage target) {
        target.addDependency(this);
    }

    /**
     * 获取不参与持久层的样本实例。
     * <p>
     * 这些样本实例可用于：
     * <ol>
     * <li>构造单元测试。
     * <li>用于系统演示。
     * <li>用样本实例作为系统的初始数据。
     * </ol>
     *
     * <p>
     * 这里返回的实例应该是用程序精心构造的领域对象，而不是从持久层获得的实际实例。
     * <p>
     * 本 Repository 的 {@link #list()}、{@link #get(Object)} 等方法不必要涉及本集合。
     *
     * 对样本实例的择取。
     * <p>
     * 考虑到单元测试和演示目的的不同用途，构造不同的样本集合。
     * <p>
     * 一些奇怪的、诡异的、甚至内容错误的实例，因为单元测试需要一些苛刻的条件来检验系统的充分性，这些应该加入到 {@link VirtualSamplePackage#WORSE} 包中。
     * <p>
     * 一些比较正常的、面向实际的样本集合应该加入到 {@link VirtualSamplePackage#NORMAL} 包中。
     */
    protected abstract void preamble();

    protected synchronized void assemble() {
        if (assembled)
            return;

        preamble();

        assembled = true;
    }

    protected <E extends Entity<?>> void addNormalSample(E... samples) {
        for (Entity<?> sample : samples)
            addNormalSample(sample);
    }

    protected <E extends Entity<?>> void addWorseSample(E... samples) {
        for (Entity<?> sample : samples)
            addWorseSample(sample);
    }

    protected void addNormalSample(Entity<? extends Serializable> sample) {
        VirtualSamplePackage.NORMAL.addInstance(sample);
    }

    protected void addWorseSample(Entity<?> sample) {
        VirtualSamplePackage.WORSE.addInstance(sample);
    }

    public void beginLoad() {
    }

    public void endLoad() {
    }

}
