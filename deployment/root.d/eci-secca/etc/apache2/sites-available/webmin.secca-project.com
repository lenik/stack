<VirtualHost *:80>
    ServerName webmin.secca-project.com
	ServerAdmin admin@secca-project.com

	LogLevel warn
	CustomLog /var/log/apache2/webmin_access.log combined
	ErrorLog  /var/log/apache2/webmin_error.log

    ProxyPass        / http://localhost:10000/
    ProxyPassReverse / http://localhost:10000/
    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>
