package com.bee32.plover.pub.oid;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.Commons, 1, 1 })
public interface PloverOids
        extends SeccaOids {

    int[] ROOT = { 3, 12 };

    int Backend = 1;
    int Core = 2;
    int Library = 3;
    int Lifecycle = 4;
    int Modeling = 5;
    int Repr = 6;
    int Ui = 7;
    int Webui = 8;

    interface backend {
        int Sysutils = 1;
        int Diagnostics = 2;
    }

    interface core {
        int Arch = 1;
        int Inject = 2;
        int Collections = 3;
        int Expr = 4;
        int ExprSQL = 5;
        int Criteria = 6;
        int Route = 7;
        int Indexing = 8;
        int Conf = 9;
        int Servlet = 10;
        int Vcs = 11;
        int Test = 12;
    }

    interface library {
        int Commons = 1;
        int Internet = 2;
        int Javascript = 3;
        int ORM = 4;
        int ORMExt = 5;
    }

    interface lifecycle {
        int Cache = 1;
        int Session = 2;
        int Stateflow = 3;
    }

    interface modeling {
        int Model = 1;
        int Disp = 2;
        int View = 3;
    }

    interface repr {
        int X2 = 1;
        int Restful = 2;
        int Resource = 3;

        interface x2 {
            int Html = 1;
            int Pdf = 2;
            int Xml = 3;
            int Csv = 4;
            int Flex = 5;
            int Mso = 6;
        }
    }

    interface ui {
        int Widgets = 1;
    }

    interface webui {
        int Facelets = 1;
    }

}
