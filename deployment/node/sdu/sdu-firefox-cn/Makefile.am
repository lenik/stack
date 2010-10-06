include libauto/coolauto.am

pluginsdir = ${libdir}/mozilla/plugins

plugins_DATA = \
    aliedit/aliedit.so \
    aliedit/aliedit.xpt

aliedit/aliedit.so aliedit/aliedit.xpt:
	mkdir -p ${srcdir}/aliedit
	cd ${srcdir}/aliedit; \
	wget -qO- http://blog.alipay.com/wp-content/2008/10/aliedit.tar.gz | tar xz

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
