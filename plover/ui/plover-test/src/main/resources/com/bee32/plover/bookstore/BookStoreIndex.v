<html>
	<head>
		<title>E/R $it.name</title>
	</head>
	<body>
		<ul>
			<h1>Bookstore Index</h1>
			#foreach ($ent in $it.list())
			<li>
				<a href="$ent.id">
					$ent.id
				</a>
				$ent

				(version=$ent.version)

				<a href="$ent.id?X=delete">delete</a>
			</li>
			#end
		</ul>
		<div>
			<a href="?X=createForm">Create new book</a>
		</div>
	</body>
</html>
