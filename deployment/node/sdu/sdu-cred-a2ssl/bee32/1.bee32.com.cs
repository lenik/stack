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
        1.1.bee32.com
        2.1.bee32.com
        3.1.bee32.com
        4.1.bee32.com
        5.1.bee32.com
        6.1.bee32.com
        7.1.bee32.com
        8.1.bee32.com
        9.1.bee32.com
        10.1.bee32.com
        11.1.bee32.com
        12.1.bee32.com
        13.1.bee32.com
        14.1.bee32.com
        15.1.bee32.com
        16.1.bee32.com
        17.1.bee32.com
        18.1.bee32.com
        19.1.bee32.com
    )

    main "$@"
