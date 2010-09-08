#!/bin/sh

GROUPADD=groupadd
USERADD="useradd -s /bin/bash"

$GROUPADD -r dev

$USERADD -r -p H6KLaFgi -G dev -d /home/appserv appserv

exit 0
