
include libauto/coolauto.am


setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh \
	post-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in post-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh    post-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

post-remove.sh: post-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/post-remove.sh.in >$@
