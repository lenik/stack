#!/bin/bash

destfile="$1"

if [ -z "$destfile" ]; then
    echo "Dest file isn't specified: $destfile"
    exit 1
fi

echo "Find latest jdbc jar"
if ! jar_url=`grepurl -Em1 '[^"]+8.4-[0-9]*.jdbc4.jar' http://jdbc.postgresql.org/download.html`; then
    echo "    Failed to search the latest jar. "
    exit 1
fi

basename="${jar_url##*/}"

if [ -f "$destfile" ]; then
    echo "    Already there: $destfile"
    exit 0
fi

echo "Download $jar_url"
if ! jar_file=`wgetc "$jar_url"`; then
    echo "    Failed to download"
    exit 1
fi

echo "Put jar in $destfile"
install -m 644 "$jar_file" "$destfile"

exit 0
