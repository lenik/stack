package com.bee32.icsf;

import com.bee32.plover.pub.oid.SeccaOids;

public interface IcsfOids
        extends SeccaOids {

    int[] ROOT = { 3, 7 };

    int Principal = 1;
    int Acl = 2;
    int Sso = 3;
    int Jaas = 4;
    int Jca = 5;
    int Pkcs11 = 6;
    int Ldap = 7;
    int Kerberos = 8;
    int Smartcard = 9;

    int Rack = 10;
    int Audit = 11;

    interface rack {
        int Api = 1;
        int Monitor = 2;
        int Analyzer = 3;
        int Cleaner = 4;
        int Db = 5;

        interface monitor {
            int Appserv = 1;
            int Syslogs = 2;
            int Weblogs = 3;
            int Tcpip = 4;
        }
    }

}
