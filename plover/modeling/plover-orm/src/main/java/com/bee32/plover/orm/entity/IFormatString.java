package com.bee32.plover.orm.entity;

import com.bee32.plover.util.PrettyPrintStream;

public interface IFormatString {

    void toString(PrettyPrintStream out, EntityFormat format);

}
