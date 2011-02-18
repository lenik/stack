package com.bee32.plover.repr.renderer;

import javax.free.IFormatDumpPreparation;
import javax.free.IStreamOutputTarget;
import javax.free.IStreamWritePreparation;

public interface IDisplay
        extends IStreamOutputTarget {

    @Override
    IStreamWritePreparation forWrite()
            throws java.io.IOException;

    @Override
    IFormatDumpPreparation forDump()
            throws java.io.IOException;

}
