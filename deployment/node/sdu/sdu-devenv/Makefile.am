include libauto/coolauto.am

cronhourlydir = ${sysconfdir}/cron.hourly

do_subst2 = $(do_subst) -e 's,[@]cronhourlydir[@],$(cronhourlydir),g'

dist_cronhourly_SCRIPTS = \
	update-secca-env

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst2) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst2) <$(srcdir)/pre-remove.sh.in >$@
