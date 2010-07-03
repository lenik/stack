function su() {
    if [ $# = 0 ]; then
        sudo su --login
    else
        /bin/su --login "$@"
    fi
}
