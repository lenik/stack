<VirtualHost *:443>
    ServerName proxy.99jsj.com
    ServerAlias *.proxy.99jsj.com
	ServerAdmin admin@example.com

    SSLEngine on
    SSLCertificateFile    /etc/apache2/certs/www.99jsj.com.crt

	LogLevel warn
	CustomLog /var/log/apache2/99proxy_access.log combined
	ErrorLog  /var/log/apache2/99proxy_error.log

    ProxyRequests On
#    ProxyPass        / http://markmail.org/
    <Proxy *>
        Order allow,deny
        Allow from all
    </Proxy>

    RewriteEngine On
    RewriteCond %{HTTP_HOST} ^(.*).proxy.99jsj.com
    RewriteRule ^(.*)   http://%1$1 [P,L]

    #ProxyHTMLInterp on
    #ProxyHTMLURLMap http://([^/]+) https://$1.proxy.99jsj.com [R,L]

</VirtualHost>
