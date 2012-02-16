#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=单元簇
    DN_CN=智恒云计算-总簇
    DN_emailAddress=ca@bee32.com

    domains=(
        www.bee32.com
        ca.bee32.com
        demo.bee32.com
        track.bee32.com
        help.bee32.com
        pg.bee32.com
        my.bee32.com
        main.bee32.com
    )

    main "$@"
