include libauto/coolauto.am

cadir       = ${datadir}/ca-certificates
bee32cadir  = ${cadir}/bee32.com
sslcertsdir = ${sysconfdir}/ssl/certs

dist_bee32ca_DATA = \
    bee32.crt \
    bee32/1.bee32.com.crt \
    bee32/cdn.bee32.com.crt \
    bee32/main.bee32.com.crt

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

install-data-hook:
	mkdir -p ${DESTDIR}${sslcertsdir}/
	ln -snf ${bee32cadir}/*.crt ${DESTDIR}${sslcertsdir}/

