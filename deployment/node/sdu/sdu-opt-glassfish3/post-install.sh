#!/bin/bash

__FILE__=`readlink -f $0`
__DIR__="${__FILE__%/*}"

ver=3.0.1
instdir=/opt/java/glassfish-$ver

if [ ! -d /opt/java ]; then
    echo "Create /opt/java/"
    mkdir -p /opt/java
fi

if [ ! -d $instdir/glassfish/bin ]; then

       mode=zip
     sh_url=http://download.java.net/glassfish/$ver/release/glassfish-$ver-unix.sh
    zip_url=http://download.java.net/glassfish/$ver/release/glassfish-$ver.zip

    case "$mode" in
    sh)
        if [ ! -d /opt/java/jdk-6 ]; then
            echo "The sh-mode glassfish installer requires sdu-opt-jdk6. "
            exit 1
        fi

        if ! file_sh=`wgetc -m755 $sh_url`; then
            echo "Failed to download self-extract archive"
            exit 1
        fi

        # -a <answerfile>
        # -l <Log Directory>
        # -q Logging level set to WARNING
        # -v Logging level set to FINEST
        # -s run this application silent mode
        # -j <javahome>
        # -n <savefile>

        # FIX: dir(answer_file) must be writable...
        answer_file=/tmp/glassfishv3.ans.$$.$RANDOM
        cp -f $__DIR__/glassfishv3.ans $answer_file

        if [ ! -f $answer_file ]; then
            echo "answer-file $answer_file doesn't exist. "
            exit 1
        fi

        echo "Run glassfish installer $file_sh"
        # JAVA_HOME=/opt/java/jdk-6

        # must pre-create the directory.
        install -d -o appserv -g dev /opt/java/glassfish-$ver

        if ! sudo -uappserv "$file_sh" -s -a "$answer_file"; then
            echo "Failed to install glassfish"
            exit 1
        fi
        rm -f $answer_file
        ;;

    zip)
        if ! file=`wgetc $zip_url`; then
            echo "Failed to download zip archive"
            exit 1
        fi

        # must pre-create the directory.
        install -d -o appserv -g dev /opt/java/glassfishv3

        if ! sudo -uappserv unzip "$file" -d /opt/java; then
            echo "Unzip failed. "
            exit 1
        fi

        mv /opt/java/glassfishv3 /opt/java/glassfish-$ver
        ;;
    esac
fi


echo "Link glassfish -> glassfish-$ver"
    cd /opt/java
    if ! ln -snf glassfish-$ver glassfish; then
        echo "  failed to create symlink"
        exit 1
    fi

echo "Delete the default domain1"
    sudo -uappserv glassfish/glassfish/bin/asadmin stop-domain domain1
    sudo -uappserv glassfish/glassfish/bin/asadmin delete-domain domain1

# install/upgrade the pkg utility
    # glassfish/bin/pkg

exit 0
