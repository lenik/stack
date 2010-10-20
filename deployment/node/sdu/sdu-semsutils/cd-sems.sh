function _cd_sems() {
    local cur="${COMP_WORDS[COMP_CWORD]}"
    local svnroot=`svnrootofcwd`
    local semsroot="${svnroot#stack/sems/trunk}"
    if [ "$svnroot" = "$semsroot" ]; then
        return 1
    fi
    COMPREPLY=( $( find "$semsroot" -name pom.xml -exec sh -c \
        'pom_xml="{}"; dir="${pom_xml%/pom.xml}"; echo "${dir##*/}"' | \
        grep ^$cur) )
    return 0
} && complete -F _cd_sems cd-sems semsd
