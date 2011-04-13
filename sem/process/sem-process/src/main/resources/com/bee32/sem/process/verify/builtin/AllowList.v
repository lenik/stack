<html>
    <head>
        <title>Allow List</title>
    </head>
    <body>
        <h1>白名单审核策略 - $it.id</h1>
        <hr />

        <h2>责任人列表</h2>

        <ul>
            #foreach ($p in $it.responsibles)
            <li>
                $p
                <a href="?method=delete&id=$p.id">删除</a>
            </li>
            #end
        </ul>

        <hr />
        <ul>

        </ul>
        <a href="~new">增加</a>
    </body>
</html>
