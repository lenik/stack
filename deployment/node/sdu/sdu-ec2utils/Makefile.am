include libauto/coolauto.am

dist_sbin_SCRIPTS = \
    mount-ebs \
    nextdev \
    ec2user-init \
    ec2-update-subst

dist_man_MANS = \
    ec2-cmd-alt.1 \
    mount-ebs.1 \
    nextdev.1 \
    ec2user-init.1 \
    ec2-update-subst.1

setup_SCRIPTS = \
    ec2-cmd-alt \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
