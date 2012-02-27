package com.bee32.plover.orm.util;

import org.apache.commons.collections15.Closure;

public interface ISamplesLoader {

    void loadSamples(SamplePackage rootPackage);

    void loadSamples(SamplePackage rootPackage, Closure<NormalSamples> progress);

}
