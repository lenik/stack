package com.bee32.plover.rtx.location;

import javax.servlet.http.HttpServletRequest;

public interface ITextForRequest {

    String resolve(HttpServletRequest request);

}
