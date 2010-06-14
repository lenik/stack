<VirtualHost *:80>
    ServerName 1.dev.secca-project.com
	ServerAdmin admin@secca-project.com

	LogLevel warn
	CustomLog /var/log/apache2/dev_access.log combined
	ErrorLog /var/log/apache2/dev_error.log

    DocumentRoot /var/www/
    <Directory /var/www>
        Options Indexes FollowSymLinks
    </Directory>

    Alias /admin/my /usr/share/phpmyadmin
    <Directory /usr/share/phpmyadmin>
        Options Indexes FollowSymLinks
        DirectoryIndex index.php

        <IfModule mod_php5.c>
            php_flag magic_quotes_gpc Off
            php_flag track_vars On
            php_flag register_globals Off
            php_value include_path .
        </IfModule>

        AuthType Basic
        AuthName "phpMyAdmin"
        AuthUserFile /node/type/etc/authdb/developer.htpasswd
        Require valid-user
    </Directory>

    Alias /admin/pg /usr/share/phppgadmin/
    <Directory /usr/share/phppgadmin/>
        DirectoryIndex index.php

        Options +FollowSymLinks
        AllowOverride None

        Order allow,deny
        Allow from all

        <IfModule mod_php5.c>
            php_flag magic_quotes_gpc Off
            php_flag track_vars On
            php_value include_path .
        </IfModule>

        AuthType Basic
        AuthName "phpPgAdmin"
        AuthUserFile /node/type/etc/authdb/developer.htpasswd
        Require valid-user
    </Directory>

    ProxyPass           /admin/gf http://localhost:4848
    ProxyPassReverse    /admin/gf http://localhost:4848
    <Proxy *>
        allow from all
    </Proxy>

    Alias /dotproject /node/local/dotproject
    <Directory /node/local/>
        Options FollowSymLinks
    </Directory>

</VirtualHost>
