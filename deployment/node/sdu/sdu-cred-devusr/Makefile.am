
include libauto/coolauto.am

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in \
	users

CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

dist-hook:
	find $(distdir)/users -type d -name .svn -exec rm -fR {} \; -prune

install-data-hook:
	cp -R $(srcdir)/users $(DESTDIR)$(setupdir); \
	find $(DESTDIR)$(setupdir)/users -type d -name .svn -exec rm -fR {} \; -prune
