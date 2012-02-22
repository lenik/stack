include libauto/coolauto.am

a2confdir = ${sysconfdir}/apache2
sitesdir  = ${a2confdir}/sites-available
admindir  = ${sysconfdir}/phppgadmin

dist_sites_DATA = \
    20secca-pgsql.site

dist_admin_DATA = \
    add_server.inc.php

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
