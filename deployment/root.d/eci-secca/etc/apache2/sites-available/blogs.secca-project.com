<VirtualHost *:80>
    ServerName blogs.secca-project.com
    ServerAlias *.blogs.secca-project.com

    UseCanonicalName Off

    ServerAdmin admin@secca-project.com

    LogLevel warn
    ErrorLog /var/log/apache2/blogs_error.log
    CustomLog /var/log/apache2/blogs_access.log combined

    DocumentRoot /node/local/wordpress-mu
    <Directory /node/local/wordpress-mu>
        Options FollowSymLinks
        AllowOverride Limit Options FileInfo
        DirectoryIndex index.php
    </Directory>

</VirtualHost>
