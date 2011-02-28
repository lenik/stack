
all: parent-control.pdf

parent-control.pdf: parent-control.tex secca.bib
	-xelatex '\nonstopmode \input parent-control'
	-bibtex parent-control
	-xelatex '\nonstopmode \input parent-control'

%.pdf1: %.tex secca.bib
	-xelatex '\nonstopmode \input parent-control'
	-bibtex parent-control
	-mkdir -p .xltmp
	xelatex --output-directory .xltmp/ '\nonstopmode \input parent-control' >/dev/null
	if ! diff $@ .xltmp/parent-control.pdf; then \
            echo 2nd pass applied...; \
            mv -f .xltmp/parent-control.pdf $@; \
        fi

clean:
	rm -f *.aux *.log *.lot *.toc *.pdf *.bbl *.blg *.out
