#!/bin/bash

libdir="$1"
if [ ! -d "$libdir" ]; then
    echo "Lib dir isn't existed: $libdir"
    exit 1
fi

echo "Find latest jdbc jar"
if ! jar_url=`grepurl -Em1 '[^"]+8.4-[0-9]*.jdbc4.jar' http://jdbc.postgresql.org/download.html`; then
    echo "    Failed to search the latest jar. "
    exit 1
fi

basename="${jar_url##*/}"
put_file="$libdir/$basename"

if [ -f "$put_file" ]; then
    echo "    Already there: $put_file"
else
    echo "Download $jar_url"
    if ! jar_file=`wgetc "$jar_url"`; then
        echo "    Failed to download"
        exit 1
    fi

    echo "Put jar in $put_file"
    install -o appserv -g dev "$jar_file" "$put_file"
fi

exit 0
