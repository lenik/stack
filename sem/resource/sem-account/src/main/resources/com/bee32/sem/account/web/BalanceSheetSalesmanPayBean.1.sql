SELECT
        a2.person AS personId
        ,p.label AS personName
        ,a2.party AS partyId
        ,b.label AS partyName
        ,a2.init
        ,a2.pable
        ,a2.ped
        ,a2.balance
    FROM
        (
            SELECT
                    person
                    ,party
                    ,SUM(init) AS init
                    ,SUM(pable) AS pable
                    ,SUM(ped) AS ped
                    ,SUM(init) + SUM(pable) - SUM(ped) AS balance
                FROM
                    (
                        SELECT
                                person
                                ,party
                                ,amount AS init
                                ,0 AS pable
                                ,0 AS ped
                            FROM
                                current_account
                            WHERE
                                stereo = 'PINIT' AND_VERIFIED
                        UNION ALL
                        SELECT
                                person
                                ,party
                                ,0 AS init
                                ,SUM(amount) AS pable
                                ,0 AS ped
                            FROM
                                current_account
                            WHERE
                                stereo = 'PABLE' AND_VERIFIED
                            GROUP BY
                                person
                                ,party
                        UNION ALL
                        SELECT
                                person
                                ,party
                                ,0 AS init
                                ,0 AS pable
                                ,- SUM(amount) AS ped
                            FROM
                                current_account
                            WHERE
                                stereo = 'PED' AND_VERIFIED
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
