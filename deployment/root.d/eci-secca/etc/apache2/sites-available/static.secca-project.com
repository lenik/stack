<VirtualHost *:80>
    ServerName static.secca-project.com
    ServerAdmin admin@secca-project.com

    Alias /lib  /node/www/lib
    Alias /lib2 /node/www/lib2
    <Directory /node/www>
        Options Indexes FollowSymLinks MultiViews
        AllowOverride None
        Order allow,deny
        allow from all
    </Directory>

    LogLevel error
    ErrorLog /var/log/apache2/static_error.log
    CustomLog /var/log/apache2/static_access.log combined

</VirtualHost>
