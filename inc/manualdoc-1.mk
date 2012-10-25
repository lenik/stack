# vim: set filetype=make :

g_incdir := $(patsubst %/,%,$(dir $(lastword $(MAKEFILE_LIST))))

tex_template := $(g_incdir)/manualdoc-1.tex
tex_libmanual := $(g_incdir)/libmanual.tex

manual-1.pdf: manual.tex history.tex $(tex_template) $(tex_libmanual)
	cooltex -abvjmanual-1 -DDIRNAME="$(g_incdir)" -Dmodtitle="$(TITLE)" -Dmodsubtitle="$(SUBTITLE)" "$(tex_template)"

history.tex: manual.tex
	CWD="$$PWD"; cd ..; \
	gitcl . | gitcl2 -r -t '' -T \
		--Tv=版本 \
		--Td=日期 \
		--Tl=变更项目 \
		--Ts='c|c|>{\sl}p{11.5cm}' \
		--Th='\caption{版本历史} \\ ' \
		--Tb='$$\circ$$' \
		>"$$CWD/history.tex"

clean:
	rm -f *.aux *.log *.lot *.ind *.idx *.ilg *.pdf *.bbl *.blg *.out
	rm -f history.tex

