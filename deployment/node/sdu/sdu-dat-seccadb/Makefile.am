include libauto/coolauto.am

crondir	= ${sysconfdir}/cron.daily

PG_CREATE_SHORTCUTS = pg-create-shortcuts

dist_bin_SCRIPTS = \
	seccadb-backup \
	seccadb-restore

dist_cron_SCRIPTS = \
	seccadb-backup.job

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = \
	post-install.sh \
	pre-remove.sh \
	secca-backup \
	secca-restore

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

# XXX_PROG_PG_CREATE_SHORTCUTS, Otherwise AM is away from debian/control.
install-data-hook:
	$(PG_CREATE_SHORTCUTS) -nseccadb -p1063 $(DESTDIR)$(bindir)
