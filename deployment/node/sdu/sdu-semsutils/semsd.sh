function _cd_sems() {
    local cur="${COMP_WORDS[COMP_CWORD]}"
    local semsroot
    if ! semsroot=`vcscmd rootpath .`; then
        return 1
    fi
    local pomdirs="$semsroot/.pomdirs"
    local ver=0
    if [ -f "$pomdirs" ]; then
        ver=`stat -c%Y "$pomdirs"`
    fi
    local cver=`date +%s`
    if [ $(( (cver - ver) / 3600 )) -gt 240 ]; then
        find "$semsroot" -name pom.xml \
        | while read f; do
            dir="${f%/pom.xml}"
            echo "${dir##*/}"
        done >"$pomdirs"
    fi

    COMPREPLY=( $( grep "^$cur" "$pomdirs" ))
    return 0
} && complete -F _cd_sems semsd
