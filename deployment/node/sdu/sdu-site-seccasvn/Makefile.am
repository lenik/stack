
include libauto/coolauto.am

a2confdir     = ${sysconfdir}/apache2
siteconfdir	  = ${a2confdir}/sites-available
xinetdconfdir = ${sysconfdir}/xinetd.d

dist_siteconf_DATA = \
	apache2/secca-svn.conf

dist_xinetdconf_DATA = \
	xinetd.d/svn-1030
#	cvs-1031

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
