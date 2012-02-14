#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=智恒云计算-公共数据分发网
    DN_CN=智恒云计算-公共数据分发网
    DN_emailAddress=ca@bee32.com

    domains=(
        cdn.bee32.com
        st1.cdn.bee32.com
        st2.cdn.bee32.com
        st3.cdn.bee32.com
    )

    main "$@"
