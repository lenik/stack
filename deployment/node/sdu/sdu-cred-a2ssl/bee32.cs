#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=智恒软件
    DN_CN=海宁市智恒软件有限公司
    DN_emailAddress=ca@bee32.com

    main "$@"
