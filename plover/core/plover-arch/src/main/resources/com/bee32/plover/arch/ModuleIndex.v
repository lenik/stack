<html>
<head>
<title>Module $it.name</title>
</head>
<body>
<h1>Module $it.name</h1>
<h2>Entries</h2>
<hr>
#foreach ($loc in $it.childNames)
#set ($target = $it.getChild($loc))
    <div>
        $loc - $target
        <a href="$loc">contents</a>
        <a href="$loc/">index</a>
    </div>
#end
<hr>
<a href="credit">CREDIT</a>
</body>
</html>
