package com.bee32.plover.orm.util;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.context.TestDataConfig;

@Import({ TestDataConfig.class })
@Lazy
public class WiredDaoTestConfig {

}
