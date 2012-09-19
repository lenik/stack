# vim: set filetype=make :

g_incdir := $(patsubst %/,%,$(dir $(lastword $(MAKEFILE_LIST))))

tex_template := $(g_incdir)/manualpro.tex
tex_libmanual := $(g_incdir)/libmanual.tex

manualpro.pdf: profile.tex $(tex_template) $(tex_libmanual)
	xelatex -jobname=manualpro '\nonstopmode \def\DIRNAME{$(g_incdir)} \def\modtitle{$(TITLE)} \def\modsubtitle{$(SUBTITLE)} \input $(tex_template)'

clean:
	rm -f *.aux *.log *.lot *.ind *.idx *.ilg *.pdf *.bbl *.blg *.out
