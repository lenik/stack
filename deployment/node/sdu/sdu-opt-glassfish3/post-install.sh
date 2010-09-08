#!/bin/sh

ver=3.0.1
instdir=/opt/java/glassfish-$ver

cd /opt/java

if [ ! -d $instdir ]; then

    if ! file=`wgetc http://download.java.net/glassfish/$ver/release/glassfish-$ver.zip`; then
        echo "Failed to download glassfish $ver"
        exit 1
    fi

    if ! unzip "$file"; then
        echo "Unzip failed. "
        exit 1
    fi

    mv glassfishv3 glassfish-$ver

fi

ln -snf glassfish-$ver glassfish
