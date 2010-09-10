
include libauto/subst.am

nodedir = /node
domaindir = ${nodedir}/domains/seccaw
dblibdir = ${domaindir}/lib

dist_pkgdata_SCRIPTS = \
	download-latest.sh

pkgdata_SCRIPTS = \
	post-install.sh \
	post-remove.sh

dblib_DATA =

EXTRA_DIST = post-install.sh.in post-remove.sh.in
CLEANFILES = post-install.sh    post-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

post-remove.sh: post-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/post-remove.sh.in >$@

install-data-hook:
	$(srcdir)/download-latest.sh $(DESTDIR)$(dblibdir)
