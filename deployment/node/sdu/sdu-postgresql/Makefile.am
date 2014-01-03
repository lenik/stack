include libauto/project.am

# Require postgresqldir.

dist_sbin_SCRIPTS = \
    hba

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

sudoers_DATA = \
    pg_sudo

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
