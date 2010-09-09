
include libauto/subst.am

sysconfdir = /etc
initdir = ${sysconfdir}/init
sysvdir = ${sysconfdir}/init.d

dist_sbin_SCRIPTS = \
	seccaw \
	seccaw-admin

dist_man_MANS = \
	seccaw.1

# dist_init_DATA = \
# 	seccaw.conf

dist_sysv_SCRIPTS = \
	init.d/seccawctl

pkgdata_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
