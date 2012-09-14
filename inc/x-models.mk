
SCHEMASPY = ~/private/chrome/schemaSpy_5.0.0.jar
M2_LOCAL = ~/.m2/repository
PGSQL_JAR = $(M2_LOCAL)/postgresql/postgresql/8.4-702.jdbc4/postgresql-8.4-702.jdbc4.jar

all: models.local models.p13

models.local:

models.p13:
	echo Generate SchemaSpy models from local playdb database.
	java -jar $(SCHEMASPY) \
	    -t pgsql -dp $(PGSQL_JAR) \
	    -host 192.168.1.13:1063 -db semsdb -s public \
	    -u semsadmin -p MxDkUWl1 \
	    -o schemaspy-models-p13
