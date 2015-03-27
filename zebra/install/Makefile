
# DB=foobar_db make create
# DB=foobar_db make all
# DB=foobar_db make reset

DATABASE = ${DB}
PSQL_S = psql -h localhost -p 1063 -1 -U postgres
PSQL_U = psql -h localhost -p 1063 -1 -U semsadmin

all: check utils lily zebra view
	@echo "NOTICE: Remember to 'make conv' if you have old data."

drop: check
	dropdb -h localhost -p 1063 -U postgres ${DATABASE}

create: check
	createdb \
	    -h localhost -p 1063 \
	    -U postgres \
	    -T template0 \
	    -E utf-8 \
	    -l zh_CN.UTF8 \
	    ${DATABASE}

check:
	@if [ -z "${DATABASE}" ]; then \
		echo "ERROR: Database isn't specified." >&2; \
		exit 1; \
	fi

utils: check
	cd /mnt/istore/projects/uni/database/pgsqls; \
	for f in *.sql; do \
		echo "Execute $$f"; \
		$(PSQL_U) -d ${DATABASE} -f "$$f"; \
	done

lily: utils
	for f in 0*.sql; do \
		echo "Execute $$f"; \
		$(PSQL_U) -d ${DATABASE} -f "$$f"; \
	done

zebra: lily
	for f in [12]*.sql; do \
		echo "Execute $$f"; \
		$(PSQL_U) -d ${DATABASE} -f "$$f"; \
	done

view: lily
	for f in [3]*.sql; do \
		echo "Execute $$f"; \
		$(PSQL_U) -d ${DATABASE} -f "$$f"; \
	done

conv: zebra
	for f in 9*.sql; do \
		echo "Execute $$f"; \
		$(PSQL_U) -d ${DATABASE} -f "$$f"; \
	done

reset: check
	- $(PSQL_S) -d ${DATABASE} -c 'drop schema public cascade'
	- $(PSQL_S) -d ${DATABASE} -c 'create schema public authorization semsadmin'
