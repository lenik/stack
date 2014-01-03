include libauto/coolauto.am

sitesdir    = ${sysconfdir}/apache2/sites-available
siteconfdir = ${sysconfdir}/apache2/sites-conf
homedir     = /home/appserv

sites_DATA = \
	sdu-archiva.conf

siteconf_DATA = \
	archiva.rules

home_DATA =

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in owner
CLEANFILES = post-install.sh    pre-remove.sh

dist-hook:
	find $(distdir)/owner -type d -name .svn -exec rm -fR {} \; -prune

install-data-hook:
	mkdir -p $(DESTDIR)$(homedir)
	cp -RT $(srcdir)/owner $(DESTDIR)$(homedir)
	find $(DESTDIR)$(homedir) -type d -name .svn -exec rm -fR {} \; -prune

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
