SELECT
        SUM(init) AS init
        ,SUM(pable) AS pable
        ,SUM(ped) AS ped
        ,SUM(init) + SUM(pable) - SUM(ped) AS balance
    FROM
        (
            SELECT
                    amount AS init
                    ,0 AS pable
                    ,0 AS ped
                FROM
                    current_account
                WHERE
                    stereo = 'PINIT' AND_VERIFIED
            UNION ALL
            SELECT
                    0 AS init
                    ,SUM(amount) AS pable
                    ,0 AS ped
                FROM
                    current_account
                WHERE
                    stereo = 'PABLE' AND_VERIFIED
            UNION ALL
            SELECT
                    0 AS init
                    ,0 AS pable
                    ,- SUM(amount) AS ped
                FROM
                    current_account
                WHERE
                    stereo = 'PED' AND_VERIFIED
        ) a1;
