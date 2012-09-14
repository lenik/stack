# vim: set filetype=make :

g_incdir := $(dir $(lastword $(MAKEFILE_LIST)))

tex_template := $(g_incdir)/manualdoc-1.tex

manual.pdf: manual.tex $(tex_template)
	xelatex -jobname=manual '\nonstopmode \def\modtitle{$(TITLE)} \def\modsubtitle{$(SUBTITLE)} \input $(tex_template)'

changes.tex:

clean:
	rm -f *.aux *.log *.lot *.ind *.idx *.ilg *.pdf *.bbl *.blg *.out

