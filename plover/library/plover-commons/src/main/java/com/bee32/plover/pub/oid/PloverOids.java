package com.bee32.plover.pub.oid;

@Oid({ 3, 12, PloverOids.library, PloverOids.libraryCommons, 1, 1 })
public interface PloverOids {

    int[] ROOT = { 3, 12 };

    int backend = 1;
    int core = 2;
    int library = 3;
    int lifecycle = 4;
    int modeling = 5;
    int repr = 6;
    int ui = 7;
    int webui = 8;

    int backendSysutils = 1;
    int backendDiagnostics = 2;

    int coreArch = 1;
    int coreInject = 2;
    int coreCollections = 3;
    int coreExpr = 4;
    int coreExprSQL = 5;
    int coreCriteria = 6;
    int coreRoute = 7;
    int coreIndexing = 8;
    int coreConf = 9;
    int coreServlet = 10;
    int coreVcs = 11;
    int coreTest = 12;

    int libraryCommons = 1;
    int libraryORM = 2;
    int libraryJavascript = 3;
    int libraryInternet = 4;

    int lifecycleCache = 1;
    int lifecycleSession = 2;
    int lifecycleStateflow = 3;

    int modelingModel = 1;
    int modelingDisp = 2;
    int modelingView = 3;

    int reprRepr = 1;
    int reprRestful = 2;

    int reprReprHtml = 1;
    int reprReprPdf = 2;
    int reprReprXml = 3;
    int reprReprCsv = 4;
    int reprReprFlex = 5;
    int reprReprMso = 6;

    int uiWidgets = 1;

    int webuiWebwidgets = 1;

}
