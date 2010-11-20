include libauto/coolauto.am

dist_bin_SCRIPTS = \
    trac-set-admin

bin_SCRIPTS = \
    trac-svn

dist_doc_DATA = \
    doc/faq

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in trac-svn.in doc
CLEANFILES = post-install.sh    pre-remove.sh    trac-svn

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

trac-svn: trac-svn.in Makefile
	$(do_subst) <$(srcdir)/trac-svn.in >$@
