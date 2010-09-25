include libauto/coolauto.am

bindconfdir   = ${sysconfdir}/bind
bindseccadir  = ${bindconfdir}/secca
cronhourlydir = ${sysconfdir}/cron.hourly

do_subst2 = $(do_subst) \
	-e 's,[@]bindconfdir[@],$(bindconfdir),g' \
	-e 's,[@]cronhourlydir[@],$(cronhourlydir),g'

# update-secca-env is obsolete, using local bind instead.
dist_cronhourly_SCRIPTS =

dist_bindsecca_DATA = \
	alias.db

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh \
	update-secca-env

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst2) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst2) <$(srcdir)/pre-remove.sh.in >$@
