SELECT
        a1.party AS partyId
        ,a1.begin_time
        ,b.label AS partyName
        ,a1.label
        ,a1.t
        ,a1.bill_no
        ,a1.rable
        ,a1.red
        ,a1.balance
    FROM
        (
            SELECT
                    TO_DATE('2000-01-01', 'yyyy-MM-dd') AS begin_time
                    ,party
                    ,label
                    ,'期初' AS t
                    ,bill_no
                    ,0 AS rable
                    ,0 AS red
                    ,amount AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'RINIT' AND_VERIFIED
            UNION ALL
            SELECT
                    begin_time
                    ,party
                    ,label
                    ,'应收单' AS t
                    ,bill_no
                    ,amount AS rable
                    ,0 AS red
                    ,0 AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'RABLE' AND_VERIFIED
            UNION ALL
            SELECT
                    begin_time
                    ,party
                    ,label
                    ,'收款单' AS t
                    ,bill_no
                    ,0 AS rable
                    ,- amount AS red
                    ,0 AS balance
                FROM
                    current_account
                WHERE
                    stereo = 'RED' AND_VERIFIED
        ) a1
            LEFT JOIN party b
                ON a1.party = b.id
    ORDER BY
        a1.party
        ,a1.begin_time;
