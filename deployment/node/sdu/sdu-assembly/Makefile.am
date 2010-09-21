
include libauto/coolauto.am

dist_sbin_SCRIPTS = \
    commitnode \
    exportnode \
    installassembly \
    installnode \
    postnode \
    removenode \
    updatenode

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

%: %.in Makefile
	$(do_subst) <$< >$@
