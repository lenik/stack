package com.bee32.plover.restful.resource;

public class FileResourceFactory
        extends ResourceFactory {

    @Override
    public String getType() {
        return "file";
    }

    @Override
    public FileResource resolve(String path) {
        return new FileResource(path);
    }

}
