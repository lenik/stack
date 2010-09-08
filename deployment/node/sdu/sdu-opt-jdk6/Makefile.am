
include libauto/subst.am

pkgdata_SCRIPTS = \
    post-install.sh

EXTRA_DIST = post-install.sh.in
CLEANFILES = post-install.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@
