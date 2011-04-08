<html>
    <head>
        <title>E/R $it.name</title>
    </head>
    <body>
        <ul>
            <h1>E/R $it.name</h1>
            <hr />
            #foreach ($ent in $it.list())
            <li>
                <a href="$ent.id">$ent.id</a>
                <pre>$ent</pre>
            </li>
            #end
        </ul>
        <hr />
        <a href="?X=createForm">Create</a>
        <div>
            <a href="..">Module Index</a>
        </div>
    </body>
</html>
