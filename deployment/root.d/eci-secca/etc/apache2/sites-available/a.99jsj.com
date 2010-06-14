<VirtualHost *:80 *:443>
    ServerName a.99jsj.com
    ServerAdmin xjl@99jsj.com
    ServerAlias mail.99jsj.com
    ServerAlias mail2.99jsj.com

    #SSLEngine on
    #SSLCertificateFile    /etc/apache2/certs/www.99jsj.com.crt
    #SSLCertificateKeyFile /etc/apache2/certs/www.99jsj.com.crt

    LogLevel warn
    CustomLog /var/log/apache2/99jsj_access.log combined
    ErrorLog  /var/log/apache2/99jsj_error.log

    ProxyPass        / http://mail.google.com/a/99jsj.com
    ProxyPassReverse / http://mail.google.com/a/99jsj.com
    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>
