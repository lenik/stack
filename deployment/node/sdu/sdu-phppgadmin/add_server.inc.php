<?php
function add_server($desc, $host, $port = 5432,
                    $sslmode = 'allow', $defdb = 'template1',
                    $pgdump = '/usr/bin/pg_dump',
                    $pgdumpall = '/usr/bin/pg_dumpall') {
    global $conf;
    $server['desc'] = $desc;
    $server['host'] = $host;
    $server['port'] = $port;
    $server['sslmode'] = $sslmode;
    $server['defaultdb'] = $defdb;
    $server['pg_dump_path'] = $pgdump;
    $server['pg_dumpall_path'] = $pgdumpall;
    $n = sizeof($conf['servers']);
    $conf['servers'][$n] = $server;
}
?>
