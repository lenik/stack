package com.bee32.plover.restful.request;

public class VerbDissolver
        implements ISuffixDissolver {

    @Override
    public boolean desolveSuffix(String name, ResourceRequest model) {
        return false;
    }

}
