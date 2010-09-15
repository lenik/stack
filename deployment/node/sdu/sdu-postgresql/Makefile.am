include libauto/coolauto.am

# Require postgresqldir.

pglibdir = /usr/lib/postgresql/8.4

dist_sbin_SCRIPTS = \
    hba \
	pg-create-shortcuts

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh \
	rm-pglibsyms

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

install-data-hook:
	test -d $(pglibdir)
	cd $(DESTDIR)$(sbindir); \
		ln -snf $(pglibdir)/bin/* .
	@echo Please run $(DESTDIR)$(pkgdatadir)/post-install.sh

uninstall-hook:
	test -d $(pglibdir)/bin
	cd $(pglibdir)/bin; \
		for f in *; do rm -f $(DESTDIR)$(sbindir)/$$f; done
	@echo Please run $(DESTDIR)$(pkgdatadir)/pre-remove.sh

