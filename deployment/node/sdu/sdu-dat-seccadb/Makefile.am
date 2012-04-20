include libauto/coolauto.am

crondir	= ${sysconfdir}/cron.daily

PG_CREATE_SHORTCUTS = pg-create-shortcuts

dist_bin_SCRIPTS = \
	baksplit \
	seccadb-backup \
	seccadb-restore \
	seccadb-csv

dist_cron_SCRIPTS = \
	80seccadb-backup

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = ${dist_bin_SCRIPTS} \
	post-install.sh \
	pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

# XXX_PROG_PG_CREATE_SHORTCUTS, Otherwise AM is away from debian/control.
install-data-hook:
	$(PG_CREATE_SHORTCUTS) -nseccadb -p1063 $(DESTDIR)$(bindir)
