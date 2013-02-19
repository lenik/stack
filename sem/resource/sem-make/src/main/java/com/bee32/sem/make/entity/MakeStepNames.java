package com.bee32.sem.make.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class MakeStepNames
        extends StandardSamples {

    public final MakeStepName cutting = new MakeStepName();
    public final MakeStepName drilling = new MakeStepName();
    public final MakeStepName assembly = new MakeStepName();
    public final MakeStepName welding = new MakeStepName();
    public final MakeStepName metalPlating = new MakeStepName();
    public final MakeStepName sandblast = new MakeStepName();
    public final MakeStepName painting = new MakeStepName();
    public final MakeStepName loading = new MakeStepName();

    @Override
    protected void wireUp() {
        cutting.setLabel("下料");
        drilling.setLabel("钻孔");
        assembly.setLabel("装配");
        welding.setLabel("手工焊");
        metalPlating.setLabel("钣金");
        sandblast.setLabel("喷沙");
        painting.setLabel("油漆");
        loading.setLabel("装卸");
    }

}
