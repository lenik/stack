#!/bin/sh

echo "Search latest linux JDK6 bin file..."
index=http://download.java.net/jdk6/latest_binaries/
latest_file=`wget -qO- $index | grep lin32JDKbin | grep -o -m1 'jdk-.*.bin' `

if [ -z "$latest_file" ]; then
    echo "Not found. "
    exit 1
fi

url=`urljoin "$index" "$latest_file"`
echo "Download $url..."
if file=`wgetc "$url"`; then
    echo "Install $file..."
    chmod a+x "$file"
    "$file"
else
    echo "Failed to download. "
fi
