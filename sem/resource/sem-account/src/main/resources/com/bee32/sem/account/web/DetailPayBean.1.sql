SELECT
        a1.party AS partyId
        ,a1.begin_time
        ,b.label AS partyName
        ,a1.label
        ,a1.t
        ,a1.bill_no
        ,a1.pable
        ,a1.ped
        ,a1.balance
    FROM
        (
            SELECT
                    TO_DATE('2000-01-01', 'yyyy-MM-dd') AS begin_time
                    ,party
                    ,label
                    ,'期初' AS t
                    ,bill_no
                    ,0 AS pable
                    ,0 AS ped
                    ,amount AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'PINIT' AND_VERIFIED
            UNION ALL
            SELECT
                    begin_time
                    ,party
                    ,label
                    ,'应收单' AS t
                    ,bill_no
                    ,amount AS pable
                    ,0 AS ped
                    ,0 AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'PABLE' AND_VERIFIED
            UNION ALL
            SELECT
                    begin_time
                    ,party
                    ,label
                    ,'收款单' AS t
                    ,bill_no
                    ,0 AS pable
                    ,- amount AS ped
                    ,0 AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'PED' AND_VERIFIED
        ) a1
            LEFT JOIN party b
                ON a1.party = b.id
    ORDER BY
        a1.party
        ,a1.begin_time;
