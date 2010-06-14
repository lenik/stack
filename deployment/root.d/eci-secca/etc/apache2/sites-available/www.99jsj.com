<VirtualHost *:80 *:443>
    ServerName www.99jsj.com
    ServerAdmin xjl@99jsj.com

    SSLEngine on
    SSLCertificateFile    /etc/apache2/certs/www.99jsj.com.crt
    #SSLCertificateKeyFile /etc/apache2/certs/www.99jsj.com.crt

    LogLevel warn
    CustomLog /var/log/apache2/99jsj_access.log combined
    ErrorLog  /var/log/apache2/99jsj_error.log

    Alias /www/ /home/lenik/sites/99jsj/www
    <Directory /home/lenik/sites/99jsj>
        Options Indexes FollowSymLinks MultiViews
        AllowOverride None
        Order allow,deny
        allow from all
    </Directory>

    ProxyPass        / http://localhost:8080/99jsj/
    ProxyPassReverse / http://localhost:8080/99jsj/
    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>

<VirtualHost *:80 *:443>
    ServerName files.99jsj.com
    ServerAdmin xjl@99jsj.com

    SSLEngine on
    SSLCertificateFile    /etc/apache2/certs/www.99jsj.com.crt

    LogLevel warn
    CustomLog /var/log/apache2/99jsj_access.log combined
    ErrorLog  /var/log/apache2/99jsj_error.log

    DocumentRoot /home/lenik/sites/99jsj/files
    <Directory /home/lenik/sites/99jsj>
        Options Indexes FollowSymLinks MultiViews
        AllowOverride None
        Order allow,deny
        allow from all
    </Directory>

</VirtualHost>
