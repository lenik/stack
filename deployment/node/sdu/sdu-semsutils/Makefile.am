include libauto/coolauto.am

shsrcdir = ${bindir}

dist_bin_SCRIPTS = \
    svnrootofcwd

dist_shsrc_SCRIPTS = \
    cd-sems

dist_aliases_DATA = \
    semsutils.sh

dist_bashcmpl_DATA = \
    semsd.sh

dist_sbin_SCRIPTS = \
    deploy-this

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
