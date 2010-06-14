<VirtualHost *:80 *:443>
    ServerName as.dev.secca-project.com
	ServerAdmin admin@secca-project.com

    SSLEngine on
    SSLCertificateFile    /etc/ssl/certs/ssl-cert-snakeoil.pem
    SSLCertificateKeyFile /etc/ssl/private/ssl-cert-snakeoil.key

    LogLevel warn
    CustomLog /var/log/apache2/dev_access.log combined
    ErrorLog /var/log/apache2/dev_error.log

    ProxyPass           / http://localhost:4848/
    ProxyPassReverse    / http://localhost:4848/
    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>
