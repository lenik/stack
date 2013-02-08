package com.bee32.ape.oid;

import com.bee32.plover.pub.oid.SeccaOids;

public interface ApeOids
        extends SeccaOids {

    int[] ROOT = { 3, 30 };

    int Compat = 1;
    int Spec = 2;
    int Repr = 3;
    int Execution = 4;
    int Designer = 5;

    interface compat {
        int Bpmn = 1;
        int Bpel = 2;
    }

    interface execution {
        int Engine = 1;
    }

    interface repr {
        int Graph = 1;
        int Banner = 2;
        int Apex = 3;
    }

    interface spec {
        int Eap = 1;
        int More = 2;
    }

}
