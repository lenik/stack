include libauto/coolauto.am

a2confdir   = ${sysconfdir}/apache2
siteconfdir = ${a2confdir}/sites-conf

do_subst2 = $(do_subst) \
    -e 's,[@]a2confdir[@],$(a2confdir),g' \
    -e 's,[@]siteconfdir[@],$(siteconfdir),g'

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst2) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst2) <$(srcdir)/pre-remove.sh.in >$@
