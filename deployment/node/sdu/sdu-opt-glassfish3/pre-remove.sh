#!/bin/sh

instdir=/opt/java/glassfish-3.0.1

if [ -d $instdir ]; then

    echo "Remove $instdir"
    rm -fr /opt/java/glassfish-3.0.1

    rm -f  /opt/java/glassfish

fi
