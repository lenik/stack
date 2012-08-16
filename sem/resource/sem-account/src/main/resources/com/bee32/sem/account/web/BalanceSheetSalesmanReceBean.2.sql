SELECT
        SUM(init) AS init
        ,SUM(rable) AS rable
        ,SUM(red) AS red
        ,SUM(init) + SUM(rable) - SUM(red) AS balance
    FROM
        (
            SELECT
                    amount AS init
                    ,0 AS rable
                    ,0 AS red
                FROM
                    current_account
                WHERE
                    stereo = 'RINIT' AND_VERIFIED
            UNION ALL
            SELECT
                    0 AS init
                    ,SUM(amount) AS rable
                    ,0 AS red
                FROM
                    current_account
                WHERE
                    stereo = 'RABLE' AND_VERIFIED
            UNION ALL
            SELECT
                    0 AS init
                    ,0 AS rable
                    ,- SUM(amount) AS red
                FROM
                    current_account
                WHERE
                    stereo = 'RED' AND_VERIFIED
        ) a1;
