<html>
	<head>
		<title>Book $it.name</title>
	</head>
	<body>
		<form>
			<input type="hidden" name="X" value="update" />
			<input type="hidden" name="id" value="$it.id" />
			<input type="hidden" name="version" value="$it.version" />

			<h1>
				Edit Book:
				<input type="text" name="name" value="$it.name" />
			</h1>

			<hr />
			Contents:

			<textarea name="content">$it.content</textarea>

			<hr />

			<input type="submit" value="Save" />
			<input type="reset" value="Reset" />
		</form>

		<div>
			<a href="?">View</a>
		</div>
	</body>
</html>
