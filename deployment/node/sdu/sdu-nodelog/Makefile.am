
include libauto/coolauto.am

crondailydir = ${sysconfdir}/cron.daily

dist_bin_SCRIPTS = \
    dg

dist_crondaily_SCRIPTS = \
    svn-add-node-logs

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
