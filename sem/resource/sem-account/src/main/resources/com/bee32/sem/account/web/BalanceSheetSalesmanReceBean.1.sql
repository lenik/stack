SELECT
        a2.person AS personId
        ,p.label AS personName
        ,a2.party AS partyId
        ,b.label AS partyName
        ,a2.init
        ,a2.rable
        ,a2.red
        ,a2.balance
    FROM
        (
            SELECT
                    person
                    ,party
                    ,SUM(init) AS init
                    ,SUM(rable) AS rable
                    ,SUM(red) AS red
                    ,SUM(init) + SUM(rable) - SUM(red) AS balance
                FROM
                    (
                        SELECT
                                person
                                ,party
                                ,amount AS init
                                ,0 AS rable
                                ,0 AS red
                            FROM
                                current_account
                            WHERE
                                stereo = 'RINIT' AND_VERIFIED
                        UNION ALL
                        SELECT
                                person
                                ,party
                                ,0 AS init
                                ,SUM(amount) AS rable
                                ,0 AS red
                            FROM
                                current_account
                            WHERE
                                stereo = 'RABLE' AND_VERIFIED
                            GROUP BY
                                person
                                ,party
                        UNION ALL
                        SELECT
                                person
                                ,party
                                ,0 AS init
                                ,0 AS rable
                                ,- SUM(amount) AS red
                            FROM
                                current_account
                            WHERE
                                stereo = 'RED' AND_VERIFIED
                            GROUP BY
                                person
                                ,party
                    ) a1
                GROUP BY
                    person
                    ,party
        ) a2
            LEFT JOIN party p
                ON a2.person = p.id
            LEFT JOIN party b
                ON a2.party = b.id;
