<VirtualHost *:80>
    ServerName  forum.secca-project.com
    ServerAdmin admin@secca-project.com

	LogLevel warn
	CustomLog /var/log/apache2/forum_access.log combined
	ErrorLog  /var/log/apache2/forum_error.log

    DocumentRoot /usr/share/phpbb3/www
    Alias /images/avatars /var/lib/phpbb3/avatars

    <Directory /usr/share/phpbb3/www/>
        <IfModule mod_php5.c>
            php_flag magic_quotes_gpc Off
            php_flag track_vars On
            php_flag register_globals Off
            php_value include_path .
        </IfModule>

        Options -Indexes +FollowSymlinks

        DirectoryIndex index.php

        AllowOverride None
        order allow,deny
        allow from all
   </Directory>

</VirtualHost>
