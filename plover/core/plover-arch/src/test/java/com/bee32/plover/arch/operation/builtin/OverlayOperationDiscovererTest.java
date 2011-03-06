package com.bee32.plover.arch.operation.builtin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationContext;

public class OverlayOperationDiscovererTest {

    OverlayOperationDiscoverer discoverer = new OverlayOperationDiscoverer();

    /* Interface matrix... This become complex, really! */@Ignore
    @Test
    public void findListOperations()
            throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("world");

        Map<String, IOperation> operations = discoverer.getOperations(list);

        IOperation deleteOperation = operations.get("delete");
        deleteOperation.execute(list, new OperationContext(1));

        assertEquals(1, list.size());
    }

}
