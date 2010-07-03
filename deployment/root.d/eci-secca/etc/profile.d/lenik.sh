#!/bin/bash

alias d='ls -FNl --color=auto'
alias dt='d -t | tail'
alias dd='d -d'

alias cd..='cd ..'
alias cd...='cd ../..'
alias cd....='cd ../../..'

function cdd() {
    cd "$1"
    shift
    if [ $# = 0 ]; then
        d
    else
        for a in "$@"; do
            d "$a"
        done
    fi
}
alias ccd=cdd

function hello() {
	echo Hello, this is Lenik.
}

export EDITOR=vi
export PATH=$PATH:/lam/kala/bin:/lam/kala/sbin:~/bin:~/sbin

export LAPIOTA=/lam/kala
export LAM_KALA=/lam/kala

alias ps='COLUMNS=1000 ps fx'
alias up='svn update --ignore-externals'

alias du1='du --max-depth=1'
alias du2='du --max-depth=2'

alias sci='scilab -nwni'
