package com.bee32.plover.orm.context;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(TxContext.class)
@ContextConfiguration("test-ds.xml")
public class TestDataConfig {

}
