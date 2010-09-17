
include libauto/coolauto.am

sysconfdir = /etc
ec2confdir = ${sysconfdir}/ec2
substdir = ${sysconfdir}/coolcmd/subst
s_instdir = ${substdir}/inst
s_voldir = ${substdir}/vol

ec2conf_DATA = \
	private_key \
	cert

s_inst_DATA = \
	subst/inst/main-dev

s_vol_DATA = \
	subst/vol/devdata

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
