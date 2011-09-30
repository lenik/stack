#!/bin/bash

PATH=$PATH:/usr/sbin

backupdir=/mnt/istore/projects/migdata/backup

if [ -x "$backupdir/backup.sh" ]; then
    cd "$backupdir"
    ./backup.sh
fi
