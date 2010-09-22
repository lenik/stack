include libauto/coolauto.am

dist_sbin_SCRIPTS = \
	ec2-metadata

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

ec2-metadata: Makefile
	wget -q -O $@ http://s3.amazonaws.com/ec2metadata/ec2-metadata

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
