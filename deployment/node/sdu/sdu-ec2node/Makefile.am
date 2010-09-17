
include libauto/coolauto.am

dist_sbin_SCRIPTS = \
	nextdev \
	ec2user-init \
	ec2-update-subst

setup_SCRIPTS = \
	ec2-cmd \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

install-data-hook:
	if f=`wgetc -m755 http://s3.amazonaws.com/ec2metadata/ec2-metadata`; then \
		install -m755 "$$f" $(DESTDIR)$(sbindir)/ec2-metadata; \
	fi
