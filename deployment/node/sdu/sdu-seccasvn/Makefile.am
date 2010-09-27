include libauto/coolauto.am

svnrootdir = /repos/svn

svnroot_DATA =

install-data-hook:
	cd $(DESTDIR)$(svnrootdir); \
	for a in api artifacts public sandbox ssp stack test users; do \
		ln -snf /mnt/devdata/svnrepo/$$a .; \
	done

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
