include libauto/coolauto.am

textmdadir = ${perllibdir}/Text/MDA

dist_bin_SCRIPTS = \
    seqlint

dist_textmda_DATA = \
    Text/MDA/Seq.pm

setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = post-install.sh.in pre-remove.sh.in
CLEANFILES = post-install.sh    pre-remove.sh

post-install.sh: post-install.sh.in Makefile
	$(do_subst) <$(srcdir)/post-install.sh.in >$@

pre-remove.sh: pre-remove.sh.in Makefile
	$(do_subst) <$(srcdir)/pre-remove.sh.in >$@
