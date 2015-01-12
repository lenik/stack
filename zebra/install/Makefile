DATABASE = ${DB}

all: check utils lily zebra conv

check:
	@if [ -z "${DATABASE}" ]; then \
		echo "ERROR: Database isn't specified." >&2; \
		exit 1; \
	fi

utils: check
	cd /mnt/istore/projects/uni/database/pgsqls; \
	for f in *.sql; do \
		echo "Execute $$f"; \
		seccadb-psql -1 -d ${DATABASE} -f "$$f"; \
	done

lily: utils
	for f in 0*.sql; do \
		echo "Execute $$f"; \
		seccadb-psql -1 -d ${DATABASE} -f "$$f"; \
	done

zebra: lily
	for f in [12]*.sql; do \
		echo "Execute $$f"; \
		seccadb-psql -1 -d ${DATABASE} -f "$$f"; \
	done

conv: zebra
	for f in 9*.sql; do \
		echo "Execute $$f"; \
		seccadb-psql -1 -d ${DATABASE} -f "$$f"; \
	done

reset: check
	- seccadb-psql -d ${DATABASE} -c 'drop schema public cascade'
	- seccadb-psql -d ${DATABASE} -c 'create schema public'
