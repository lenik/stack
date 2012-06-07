include libauto/coolauto.am

cadir           = ${datadir}/ca-certificates
sslcertsdir     = ${sysconfdir}/ssl/certs
sslkeysdir      = ${sysconfdir}/ssl/private
bee32cadir      = ${cadir}/bee32.com

dist_bee32ca_DATA = \
    bee32.crt \
    bee32/1.bee32.com.crt \
    bee32/cdn.bee32.com.crt \
    bee32/main.bee32.com.crt

dist_sslkeys_DATA = \
    bee32.pem \
    bee32/1.bee32.com.pem \
    bee32/cdn.bee32.com.pem \
    bee32/main.bee32.com.pem

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

install-data-hook:
	mkdir -p ${DESTDIR}${sslcertsdir}/
	shopt -s nullglob; ln -snfv ${bee32cadir}/*.crt ${DESTDIR}${sslcertsdir}/

