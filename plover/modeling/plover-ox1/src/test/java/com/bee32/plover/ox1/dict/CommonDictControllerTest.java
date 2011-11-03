package com.bee32.plover.ox1.dict;

import org.junit.Assert;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.web.basic.BasicEntityController;

public class CommonDictControllerTest
        extends Assert {

    public static void main(String[] args) {
        Class<Object> infer1 = ClassUtil.infer1(CommonDictController.class, BasicEntityController.class, 0);
        System.out.println(infer1);
    }

}
