#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=
    DN_CN=海宁市智恒软件有限公司
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
    )

    main "$@"
