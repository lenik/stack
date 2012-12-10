#!/bin/bash

root=`vcs rootpath`

for p in *; do
    [ -f "$p/profile.tex" ] || continue

    mkdir -p $p/src

    echo "Profile $p"
    while read _modchap _title _path; do
        path="$root/$_path"
        echo "Merge from $path"
        cp "$path/doc/src"/* $p/src
    done < <(grep '^\\modulechapter' $p/profile.tex | tr '{}' '  ')

    randmerge=$p/randsrc
    echo -n >$randmerge

    echo "Random src -> $randmerge"
    files=("$p"/src/*)
    nfile=${#files[@]}

    for i in {1..100}; do
        index=$(( RANDOM % nfile ))

        f="${files[index]}"
        echo " -- $f"
        cat $f >>$randmerge

        files=( "${files[@]:0:index}" "${files[@]:index+1}" )
        nfile=${#files[@]}
    done
done
