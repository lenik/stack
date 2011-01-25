package com.bee32.plover.repr.view.pgrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GridMapper
        extends AbstractMapper {

    @Override
    public void map(Map<String, Object> tree) {
        List<String> paths = new ArrayList<String>(tree.keySet());

        Collections.sort(path);

    }

}
