#!/bin/sh

ver=3.0.1
instdir=/opt/java/glassfish-$ver

if [ -d $instdir ]; then
    echo "already installed: $instdir. "
    exit 0
fi

if ! file=`wgetc http://download.java.net/glassfish/$ver/release/glassfish-$ver.zip`; then
    echo "Failed to download glassfish $ver"
    exit 1
fi

cd /opt/java
if ! unzip "$file"; then
    echo "Unzip failed. "
    exit 1
fi

mv glassfishv3 $instdir
