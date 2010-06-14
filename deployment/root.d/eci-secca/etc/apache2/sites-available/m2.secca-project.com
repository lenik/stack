<VirtualHost *:80>
    ServerName  m2.secca-project.com
    ServerAlias m2.bodz.net
    ServerAdmin admin@secca-project.com

    LogLevel warn
    CustomLog /var/log/apache2/farm_access.log combined
    ErrorLog  /var/log/apache2/farm_error.log

    DocumentRoot /node/dev/share/dev-site/farm

    RewriteEngine On

    RewriteRule ^/internal/(.*)  /archiva/repository/mirror-releases/$1 [PT]
    RewriteRule ^/releases/(.*)  /archiva/repository/releases/$1 [PT]
    RewriteRule ^/snapshots/(.*) /archiva/repository/snapshots/$1 [PT]

    #Alias /releases     /archiva/repository/releases
    #Alias /snapshots    /archiva/repository/snapshots

    ProxyPass           /archiva http://localhost:8080/archiva
    ProxyPassReverse    /archiva http://localhost:8080/archiva
    <Proxy *>
        allow from all
    </Proxy>

</VirtualHost>
