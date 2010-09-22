
include libauto/coolauto.am

ec2confdir = ${sysconfdir}/ec2
substdir   = ${sysconfdir}/coolcmd/subst

ec2conf_DATA = \
    private_key \
    cert

subst_DATA =

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in subst
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

dist-hook:
	find $(distdir)/subst -type d -name .svn -exec rm -fR {} \; -prune

install-data-hook:
	cp -RT $(srcdir)/subst $(DESTDIR)$(substdir)
	find $(DESTDIR)$(substdir) -type d -name .svn -exec rm -fR {} \; -prune
