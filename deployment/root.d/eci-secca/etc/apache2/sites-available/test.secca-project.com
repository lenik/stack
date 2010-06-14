<VirtualHost *:80>
    ServerName test.secca-project.com
	ServerAdmin admin@secca-project.com

	LogLevel warn
	CustomLog /var/log/apache2/test_access.log combined
	ErrorLog  /var/log/apache2/test_error.log

    ProxyPass           /ebi-org http://localhost:8080/ebi-org
    ProxyPassReverse    /ebi-org http://localhost:8080/ebi-org

    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>
