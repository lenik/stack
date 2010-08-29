
include libauto/subst.am

# Require postgresqldir.

sbindir = ${prefix}/sbin

dist_sbin_SCRIPTS = \
    hba

EXTRA_DIST = add-symlinks

install-data-hook:
	$(srcdir)/add-symlinks $(DESTDIR)$(sbindir)

uninstall-hook:
	$(srcdir)/remove-symlinks $(DESTDIR)$(sbindir)
