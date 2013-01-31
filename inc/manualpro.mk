# vim: set filetype=make :

SHELL=/bin/bash

g_incdir := $(patsubst %/,%,$(dir $(lastword $(MAKEFILE_LIST))))

tex_template := $(g_incdir)/manualpro.tex
tex_libmanual := $(g_incdir)/libmanual.tex

all: manualpro.pdf

manualpro.pdf: profile.tex history.tex $(tex_template) $(tex_libmanual)
	cooltex -abvjmanualpro \
		-DDIRNAME="$(g_incdir)" \
		-Dmodtitle="$(TITLE)" \
		-Dmodsubtitle="$(SUBTITLE)" \
		-Dmodversion="`cat version`" \
		"$(tex_template)"

# stack/manuals/profiles/FooBar/...

version: history.tex
	grep '\hline' history.tex | head -1 | while read col1 _; do \
		: col1="\multirow{1}{*}{1.10.2}" ...; \
		ver="$${col1%\}}"; \
		ver="$${ver##*\{}"; \
		echo "$$ver" >version; \
	done

history.tex: modules
	CWD="$$PWD"; cd ../../..; \
	gitcl `cat $$CWD/modules` | gitcl2 -r -t '' -T \
		--Tv=版本 \
		--Td=日期 \
		--Tl=变更项目 \
		--Ts='c|c|>{\sl}p{11.5cm}' \
		--Th='\caption{版本历史} \\ ' \
		--Tb='$$\circ$$' \
		>"$$CWD/history.tex"

modules: profile.tex
	grep modulechapter $< | while IFS='{' read modchap title dir; do \
		echo "$${dir%\}}"; \
	done >$@

SLOC: modules
	total=0; \
	echo -n >$@; \
	while read path; do \
		read num _ < <(wcc -r "../../../$$path" | grep '<<total>>'); \
		echo "$$num $$path"; \
		((total += num)); \
	done < <(echo plover; echo icsf; grep -v plover modules) >>$@; \
	echo "$$total TOTAL" >>$@

clean:
	rm -f *.aux *.log *.lot *.ind *.idx *.ilg *.pdf *.bbl *.blg *.out
	rm -f history.tex
