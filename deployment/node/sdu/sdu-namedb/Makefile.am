include libauto/coolauto.am

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
