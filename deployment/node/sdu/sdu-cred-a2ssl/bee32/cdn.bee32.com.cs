#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=公共数据
    DN_CN=智恒云计算-公共数据分发
    DN_emailAddress=ca@bee32.com

    domains=(
        cdn.bee32.com
        *.cdn.bee32.com
    )

    main "$@"
