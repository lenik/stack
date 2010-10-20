function _cd_sems() {
    local cur="${COMP_WORDS[COMP_CWORD]}"
    local semsroot
    if ! semsroot=`svnrootofcwd stack/sems/trunk`; then
        return 1
    fi
    COMPREPLY=( $( find "$semsroot" -name pom.xml -exec sh -c \
        'pom_xml="{}"; dir="${pom_xml%/pom.xml}"; echo "${dir##*/}"' \; | \
        grep ^$cur) )
    return 0
} && complete -F _cd_sems semsd
