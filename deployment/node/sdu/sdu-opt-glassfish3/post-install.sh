#!/bin/bash

srcfile=`readlink -f $0`
srcdir="${srcfile%/*}"

ver=3.0.1
instdir=/opt/java/glassfish-$ver

cd /opt/java

if [ ! -d $instdir/glassfish/bin ]; then

    mode=sh

    case "$mode" in
    sh)
        if ! file_sh=`wgetc http://download.java.net/glassfish/$ver/release/glassfish-$ver-unix.sh`; then
            echo "Failed to download self-extract archive"
            exit 1
        fi
        chmod a+x "$file_sh"

        # -a <answerfile>
        # -l <Log Directory>
        # -q Logging level set to WARNING
        # -v Logging level set to FINEST
        # -s run this application silent mode
        # -j <javahome>
        # -n <savefile>
        answer_file=$srcdir/glassfishv3.ans
        if [ ! -f $answer_file ]; then
            echo "answer-file $answer_file doesn't exist. "
            exit 1
        fi
        if ! "$file_sh" -s -a "$answer_file"; then
            echo "Failed to install glassfish"
            exit 1
        fi
        ;;

    zip)
        if ! file=`wgetc http://download.java.net/glassfish/$ver/release/glassfish-$ver.zip`; then
            echo "Failed to download zip archive"
            exit 1
        fi

        if ! unzip "$file"; then
            echo "Unzip failed. "
            exit 1
        fi

        mv glassfishv3 glassfish-$ver
        ;;
    esac

fi

ln -snf glassfish-$ver glassfish

# install/upgrade the pkg utility
# glassfish/bin/pkg
