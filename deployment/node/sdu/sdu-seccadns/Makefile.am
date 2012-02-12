include libauto/coolauto.am

do_subst += \
    -e 's,[@]bindconfdir[@],$(bindconfdir),g' \
    -e 's,[@]seccadbdir[@],$(seccadbdir),g' \
    -e 's,[@]htmldir[@],$(htmldir),g' \
    -e 's/[@]ZONEOPTS[@]/allow-update { key bee32-key; };/g'

bindconfdir = ${sysconfdir}/bind
seccadbdir  = ${bindconfdir}/secca
sitesdir    = ${sysconfdir}/apache2/sites-available
htmldir     = ${pkgdatadir}/html

dist_seccadb_DATA = \
    named.conf \
    bee32.key \
    bee32.db \
    bee32-lan.db \
    bee32-gw.db \
    rad.db \
    bia.db \
    layout.png

dist_sites_DATA = \
    bee32-copy.site \
    bee32-reloc.site \
    bee32-dns.site

dist_html_DATA = \
    dns-update.php

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh \
    substs-db \
    substs-host

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh \
    named.conf \
    bee32.db \
    bee32-dns.site \
    dns-update.php

layout.png: bee32.db bee32-gw.db
	${srcdir}/dnsgraph -O "$@" bee32.db bee32-gw.db
