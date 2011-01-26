package com.bee32.plover.restful.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.bee32.plover.inject.IComponent;

public interface IResource
        extends IComponent {

    String getPath();

    String getHref();

    InputStream openBinary()
            throws IOException;

    Reader openText()
            throws IOException;

}
