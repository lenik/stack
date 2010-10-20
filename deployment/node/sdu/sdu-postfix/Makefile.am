include libauto/coolauto.am

dist_sbin_SCRIPTS = \
    aliases \
    canonical \
    mailing-list \
    main.cf \
    master.cf \
    qdel

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
