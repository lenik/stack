<html>
<head>
<title>Credit</title>
</head>
<body>
<h1>Credits</h1>
<hr>
#foreach ($subj in $it.subjects)
    <h2>$subj</h2>
    <div>
        <i>{ Contributors }</i>
    </div>
    <ul>
        #foreach ($c in $it.getContributors($subj))
            <li>
                $c ($c.role @ $c.organization)
            </li>
        #end
    </ul>
#end
</body>
</html>
