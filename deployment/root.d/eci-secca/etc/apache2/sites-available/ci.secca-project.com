<VirtualHost *:80>
    ServerName  ci.secca-project.com
    ServerAdmin admin@secca-project.com

    LogLevel warn
    CustomLog /var/log/apache2/farm_access.log combined
    ErrorLog  /var/log/apache2/farm_error.log

    DocumentRoot /node/dev/share/dev-site/farm

    ProxyPass        /hudson http://localhost:8080/hudson
    ProxyPassReverse /hudson http://localhost:8080/hudson
    <Proxy *>
        allow from all
    </Proxy>
</VirtualHost>
