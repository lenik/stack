package com.bee32.icsf;

public interface IcsfOids {

    int[] ROOT = { 3, 7 };

    int principal = 1;
    int acl = 2;
    int sso = 3;
    int jaas = 4;
    int jca = 5;
    int pkcs11 = 6;
    int ldap = 7;
    int kerberos = 8;
    int smartcard = 9;

    int rack = 10;
    int audit = 11;

    int rackApi = 1;
    int rackMonitor = 2;
    int rackAnalyzer = 3;
    int rackCleaner = 4;
    int rackDb = 5;

    int rackMonitorAppserv = 1;
    int rackMonitorSyslogs = 2;
    int rackMonitorWeblogs = 3;
    int rackMonitorTcpip = 4;

}
