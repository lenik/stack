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

setup_DATA = \
    trac-post-commit-hook.diff

EXTRA_DIST = post-install.sh.in pre-remove.sh.in trac-svn.in doc
CLEANFILES = post-install.sh    pre-remove.sh    trac-svn

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

trac-svn: trac-svn.in Makefile
	$(do_subst) <$(srcdir)/trac-svn.in >$@

trac-post-commit-hook.diff: trac-post-commit-hook.0 trac-post-commit-hook
	-diff -u ${srcdir}/trac-post-commit-hook.0 \
                 ${srcdir}/trac-post-commit-hook >$@
