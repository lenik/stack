#!/bin/bash
    . shlib-import mkcert.cs

    DN_C=CN
    DN_ST=浙江
    DN_O=海宁市智恒软件有限公司
    DN_OU=中央数据安全办公室
    DN_CN=海宁市智恒软件有限公司
    DN_emailAddress=ca@bee32.com

    main "$@"
