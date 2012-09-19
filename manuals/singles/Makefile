
subdirs:
	cd ../..; \
        ori="$$PWD"; \
        for m in `find -name manual.tex`; do \
            d=$${m%/manual.tex}; \
            cd "$$d"; \
            $(MAKE); \
            cd "$$ori"; \
	done
