
include libauto/subst.am

nodedir = /node

n_bindir = ${nodedir}/bin
n_sbindir = ${nodedir}/sbin
n_etcdir = ${nodedir}/etc
n_domainsdir = ${nodedir}/domains
n_datadir = ${nodedir}/data

n_bin_SCRIPTS =
n_sbin_SCRIPTS =
n_etc_DATA =
n_domains_DATA = domains/README
n_data_DATA = data/README

pkgdata_SCRIPTS = \
	post-install.sh \
	pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
