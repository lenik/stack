# vim: set filetype=make :

g_incdir := $(dir $(lastword $(MAKEFILE_LIST)))

tex_template := $(g_incdir)/manualdoc-1.tex

manual-1.pdf: manual.tex history.tex $(tex_template)
	xelatex -jobname=manual-1 '\nonstopmode \def\modtitle{$(TITLE)} \def\modsubtitle{$(SUBTITLE)} \input $(tex_template)'

history.tex: manual.tex
	CWD="$$PWD"; cd ..; \
	gitcl . | gitcl2 -r -t '' -T \
		--Tv=版本 \
		--Td=日期 \
		--Tl=变更项目 \
		--Tw=10cm \
		--Ts='c|c|>{\sl}p{10cm}' \
		--Th='\caption{版本历史} \\ ' \
		--Tb='$$\circ$$' \
		>"$$CWD/history.tex"

clean:
	rm -f *.aux *.log *.lot *.ind *.idx *.ilg *.pdf *.bbl *.blg *.out
	rm -f history.tex

