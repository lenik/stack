include libauto/coolauto.am

do_subst2 = $(do_subst) \
	-e 's,[@]bindconfdir[@],$(bindconfdir),g' \
	-e 's,[@]seccadbdir[@],$(seccadbdir),g'

bindconfdir = ${sysconfdir}/bind
seccadbdir  = ${bindconfdir}/secca

seccadb_DATA = \
	named.conf \
	seccaproject.db \
	bia.db

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh \
	substs-db \
	substs-host

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst2) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst2) <$(srcdir)/pre-remove.sh.in >$@
