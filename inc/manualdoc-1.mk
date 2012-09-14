# vim: set filetype=make :

manual.pdf: manual.tex ../manualdoc-1.tex
	#xelatex -jobname=manual '\nonstopmode \input ../manualdoc-1'
	xelatex -jobname=manual '\nonstopmode \def\modtitle{$(TITLE)} \def\modsubtitle{$(SUBTITLE)} \input ../manualdoc-1'

clean:
	rm -f *.aux *.log *.lot *.toc *.pdf *.bbl *.blg *.out
