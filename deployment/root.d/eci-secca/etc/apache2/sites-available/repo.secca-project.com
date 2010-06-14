<VirtualHost *:80>
    ServerName repo.secca-project.com
	ServerAdmin admin@secca-project.com

	ErrorLog /var/log/apache2/repo_error.log

	# Possible values include: debug, info, notice, warn, error, crit,
	# alert, emerg.
	LogLevel warn

	CustomLog /var/log/apache2/repo_access.log combined

</VirtualHost>
