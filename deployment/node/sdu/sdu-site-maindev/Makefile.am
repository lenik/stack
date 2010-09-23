
include libauto/coolauto.am

siteconfdir = ${sysconfdir}/apache2/sites-available

siteconf_DATA = \
	secca-devsite.site

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in secca-devsite.site.in
CLEANFILES = post-install.sh    pre-remove.sh	 secca-devsite.site

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

secca-devsite.site: secca-devsite.site.in Makefile
	$(do_subst) <$(srcdir)/secca-devsite.site.in >$@
