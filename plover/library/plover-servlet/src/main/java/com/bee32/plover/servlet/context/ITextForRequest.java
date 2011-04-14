package com.bee32.plover.servlet.context;

import javax.servlet.http.HttpServletRequest;

public interface ITextForRequest {

    String resolve(HttpServletRequest request);

}
