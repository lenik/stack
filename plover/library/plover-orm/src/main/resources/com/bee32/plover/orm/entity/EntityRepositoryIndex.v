<html>
<head>
<title>E/R $it.name</title>
</head>
<body>
<ul>
<h1>E/R $it.name</h1>
#foreach ($ent in $it.list())
    <li>
        <a href="$ent.primaryKey">
            $ent.id
        </a>
        $ent
    </li>
#end
</ul>
</body>
</html>
