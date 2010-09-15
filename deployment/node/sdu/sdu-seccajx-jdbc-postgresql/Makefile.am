
include libauto/coolauto.am

nodedir = /node
domaindir = ${nodedir}/domains/seccaj
dblibdir = ${domaindir}/lib

dist_pkgdata_SCRIPTS = \
	download-latest.sh

pkgdata_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

dblib_DATA =

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

install-data-hook:
	$(srcdir)/download-latest.sh $(DESTDIR)$(dblibdir)
