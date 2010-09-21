#!/bin/sh

instdir=/opt/java/glassfish-3.0.1

if [ -d $instdir ]; then

    echo "Remove $instdir (exclude domains/*)"
        # Don't remove domains:
        # rm -fr $instdir
        find $instdir -depth -path '*/domains/*' -prune -o -delete

    echo "Remove symlink /opt/java/glassfish"
    rm -f  /opt/java/glassfish

fi

exit 0
