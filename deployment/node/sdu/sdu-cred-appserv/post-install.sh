#!/bin/sh

USERADD="useradd -s /bin/bash"

$USERADD -r -p H6KLaFgi -G dev -d /home/appserv appserv

exit 0
