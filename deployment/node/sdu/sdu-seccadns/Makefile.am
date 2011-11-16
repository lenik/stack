include libauto/coolauto.am

do_subst += \
	-e 's,[@]bindconfdir[@],$(bindconfdir),g' \
	-e 's,[@]seccadbdir[@],$(seccadbdir),g'

bindconfdir = ${sysconfdir}/bind
seccadbdir  = ${bindconfdir}/secca
sitesdir	= ${sysconfdir}/apache2/sites-available

dist_seccadb_DATA = \
	named.conf \
	bee32.db \
	bee32-fw.db \
	rad.db \
	bia.db

dist_sites_DATA = \
	bee32-copy.site \
	bee32-reloc.site

setup_SCRIPTS = \
	post-install.sh \
	pre-remove.sh \
	substs-db \
	substs-host

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

