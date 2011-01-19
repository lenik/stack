include libauto/coolauto.am

do_subst += -e 's,[@]postfixdir[@],$(postfixdir),g'

postfixdir = ${sysconfdir}/postfix

SUBDIRS = postfix

dist_sbin_SCRIPTS = \
    aliases \
    canonical \
    mailing-list \
    main.cf \
    master.cf \
    qdel

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh \
    sasl.cf

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
