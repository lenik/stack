<VirtualHost *:80>
    ServerName list.secca-project.com
    ServerAdmin admin@secca-project.com

    ErrorLog /var/log/apache2/list_error.log

    # Possible values include: debug, info, notice, warn, error, crit,
    # alert, emerg.
    LogLevel warn

    CustomLog /var/log/apache2/list_access.log combined

    ScriptAlias /cgi-bin/   /usr/lib/cgi-bin/mailman/
    Alias       /images/    /usr/share/images/mailman/

    Alias       /archives/  /var/lib/mailman/archives/public/
#    Alias       /private/   /var/lib/mailman/archives/private/

    <Directory /usr/lib/cgi-bin/mailman/>
        AllowOverride None
        Options ExecCGI
        AddHandler cgi-script .cgi
        Order allow,deny
        Allow from all
    </Directory>
    <Directory /usr/share/images/mailman/>
        AllowOverride None
        Order allow,deny
        Allow from all
    </Directory>
    <Directory /var/lib/mailman/archives/>
        Options Indexes FollowSymlinks
        AllowOverride None
        Order allow,deny
        Allow from all
    </Directory>

</VirtualHost>
