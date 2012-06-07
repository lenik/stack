#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=智恒云计算-簇1
    DN_CN=智恒云计算-簇1
    DN_emailAddress=ca@bee32.com

    domains=(
        1.bee32.com
        *.1.bee32.com
    )

    main "$@"
