include libauto/coolauto.am

do_subst2 = $(do_subst) \
	-e 's,[@]domaindir[@],$(domaindir),g' \
	-e 's,[@]dblibdir[@],$(dblibdir),g'

domaindir = /var/lib/seccaj
dblibdir = ${domaindir}/lib

dblib_DATA = \
	postgresqljdbc.jar

postgresqljdbc.jar:
	$(srcdir)/download-latest.sh $@

pkgdata_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst2) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst2) <$(srcdir)/pre-remove.sh.in >$@

