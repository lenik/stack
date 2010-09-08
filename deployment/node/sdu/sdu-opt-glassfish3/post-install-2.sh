#!/bin/sh

cd /usr/sbin
    for f in pkg updatetool; do
        ln -snf /opt/java/glassfish/bin/$f .
    done

    for f in appclient asadmin asupgrade capture-schema jspc \
            package-appclient schemagen startserv stopserv \
            wscompile wsdeploy wsgen wsimport xjc; do
        ln -snf /opt/java/glassfish/glassfish/bin/$f .
    done
