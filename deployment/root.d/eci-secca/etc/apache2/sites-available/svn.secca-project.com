<VirtualHost *:80 *:443>
    ServerName svn.secca-project.com
	ServerAdmin admin@secca-project.com

    SSLEngine on
    SSLCertificateFile    /etc/ssl/certs/ssl-cert-snakeoil.pem
    SSLCertificateKeyFile /etc/ssl/private/ssl-cert-snakeoil.key

	LogLevel  warn
	ErrorLog  /var/log/apache2/svn_error.log
	CustomLog /var/log/apache2/svn_access.log combined

    DocumentRoot /node/dev/share/dev-site/farm

    <Location />
        DAV svn
        SVNParentPath /repos/svn

        AuthType Basic
        AuthName "SECCA SVN REPOSITORY"
       #AuthUserFile /node/type/etc/authdb/svndav.htpasswd
        AuthUserFile /repos/svn/conf/svn.htpasswd

        AuthzSVNAccessFile /repos/svn/conf/authz
        AuthzSVNAnonymous off
        #AuthzSVNAuthoritative on
        #AuthzSVNNoAuthWhenAnonymousAllowed on

        Require valid-user
        #<LimitExcept PROPFIND OPTIONS REPORT>
        #</LimitExcept>
    </Location>
</VirtualHost>
