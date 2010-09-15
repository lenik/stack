
include libauto/coolauto.am

sysconfdir = /etc
initdir = ${sysconfdir}/init
sysvdir = ${sysconfdir}/init.d

dist_sbin_SCRIPTS = \
	seccaj \
	seccaj-admin

dist_man_MANS = \
	seccaj.1

# dist_init_DATA = \
# 	seccaj.conf

dist_sysv_SCRIPTS = \
	init.d/seccajctl

pkgdata_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
