
include libauto/coolauto.am

siteconfdir = ${sysconfdir}/apache2/sites-available

dist_siteconf_DATA = \
    20secca-devsite.conf

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

20secca-devsite.site: 20secca-devsite.site.in Makefile
	$(do_subst) <$(srcdir)/20secca-devsite.site.in >$@
