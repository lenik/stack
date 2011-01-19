include libauto/coolauto.am

do_subst += \
    -e 's,[@]logdir[@],$(logdir),g' \
    -e 's,[@]crondailydir[@],$(crondailydir),g'

logdir = /var/log/node
crondailydir = ${sysconfdir}/cron.daily

bin_SCRIPTS = \
    dg

dist_crondaily_SCRIPTS = \
    svn-add-node-logs

dist_setup_SCRIPTS = \
    post-install.sh \
    pre-remove.sh

EXTRA_DIST = dg.in
CLEANFILES = dg

%.sh: %.sh.in Makefile
	$(do_subst) <$< >$@
