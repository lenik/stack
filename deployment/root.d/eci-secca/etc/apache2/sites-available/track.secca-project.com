<VirtualHost *:80 *:443>
    ServerName track.secca-project.com
    ServerAdmin admin@secca-project.com

    SSLEngine on
    SSLCertificateFile    /etc/ssl/certs/ssl-cert-snakeoil.pem
    SSLCertificateKeyFile /etc/ssl/private/ssl-cert-snakeoil.key

    LogLevel warn
    CustomLog /var/log/apache2/track_access.log combined
    ErrorLog  /var/log/apache2/track_error.log

    DocumentRoot /node/dev/share/dev-site/farm

    Alias /bugzilla3/doc/ /usr/share/doc/bugzilla3-doc/html/
    Alias /bugzilla3/ /usr/share/bugzilla3/web/
    <Directory "/usr/share/bugzilla3/web">
        Options Indexes
        AllowOverride none
        Order allow,deny
        Allow from all
    </Directory>
    <Directory "/usr/share/doc/bugzilla3-doc/html/">
        Options Indexes MultiViews FollowSymLinks
        AllowOverride None
        Order allow,deny
        Allow from all
    </Directory>

    ScriptAlias /cgi-bin/bugzilla3/ /usr/lib/cgi-bin/bugzilla3/
    <Directory "/usr/lib/cgi-bin">
        AllowOverride None
        Options +ExecCGI -MultiViews +SymLinksIfOwnerMatch
        Order allow,deny
        Allow from all
    </Directory>
    <Directory "/usr/lib/cgi-bin/bugzilla3">
        RewriteEngine on
        RewriteBase /cgi-bin/bugzilla3
        RewriteRule ^$ index.cgi
    </Directory>

    <Directory "/var/lib/bugzilla3/data">
        Options FollowSymLinks
        AllowOverride None
        Order allow,deny
        Allow from all
    </Directory>

    Alias /trac/ /repos/trac/chrome/htdocs/common/
    #AliasMatch ^/projects/[^/]*/chrome/(.*)$ /repos/trac/chrome/htdocs/common/$1

    <Location /projects>
        SetHandler mod_python
        PythonInterpreter main_interpreter
        PythonHandler trac.web.modpython_frontend
        PythonOption TracUriRoot /projects
        PythonOption TracEnvParentDir /repos/trac
        PythonOption TracEnvIndexTemplate /repos/trac/index.html
    </Location>

    #LoadModule auth_digest_module /usr/lib/apache2/modules/mod_auth_digest.so
    <LocationMatch /projects/[^/]+/login>
        AuthType Digest
        AuthName "trac"
        AuthDigestDomain /trac
        AuthUserFile /node/type/etc/authdb/trac.htpasswd
        Require valid-user
    </LocationMatch>

</VirtualHost>
