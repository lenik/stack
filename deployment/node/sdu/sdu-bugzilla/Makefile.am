
include libauto/coolauto.am

bindir = ${prefix}/sbin

dist_bin_SCRIPTS = \
    mybugs

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
