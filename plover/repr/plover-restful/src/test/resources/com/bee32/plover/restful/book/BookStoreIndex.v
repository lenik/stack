<html>
<head>
<title>E/R $it.name</title>
</head>
<body>
<ul>
<h1>Bookstore Index</h1>
#foreach ($ent in $it.list())
    <li>
        <a href="$ent.primaryKey">
            $ent.primaryKey
        </a>
        $ent
    </li>
#end
</ul>
</body>
</html>
