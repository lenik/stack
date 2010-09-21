
include libauto/coolauto.am

PG_CREATE_SHORTCUTS = pg-create-shortcuts

sbin_SCRIPTS =

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@

# XXX_PROG_PG_CREATE_SHORTCUTS, Otherwise AM is away from debian/control.
install-data-hook:
	$(PG_CREATE_SHORTCUTS) -nseccadb -p1063 $(DESTDIR)$(sbindir)
