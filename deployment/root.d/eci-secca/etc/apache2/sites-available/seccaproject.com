<VirtualHost *:80>
    ServerName seccaproject.com
    ServerAlias *.seccaproject.com
    ServerAdmin admin@secca-project.com

    LogLevel warn
    ErrorLog  /var/log/apache2/alias_error.log
    CustomLog /var/log/apache2/alias_access.log combined

    RewriteEngine On
    RewriteCond %{HTTP_HOST} ^(.*)seccaproject.com$
    RewriteRule ^(.*)$ http://%1secca-project.com$1 [R=301,L]
</VirtualHost>

<VirtualHost *:443>
    ServerName seccaproject.com
    ServerAlias *.seccaproject.com
    ServerAdmin admin@secca-project.com

    SSLEngine on
    SSLCertificateFile    /etc/apache2/certs/www.secca-project.com.crt

    LogLevel warn
    ErrorLog  /var/log/apache2/alias_error.log
    CustomLog /var/log/apache2/alias_access.log combined

    RewriteEngine On
    RewriteCond %{HTTP_HOST} ^(.*)seccaproject.com$
    RewriteRule ^(.*)$ https://%1secca-project.com$1 [R=301,L]
</VirtualHost>
