SELECT
        a2.org_unit AS org_unit_id
        ,p.label AS org_unit_name
        ,a2.party AS partyId
        ,b.label AS partyName
        ,a2.init
        ,a2.pable
        ,a2.ped
        ,a2.balance
    FROM
        (
            SELECT
                    org_unit
                    ,party
                    ,SUM(init) AS init
                    ,SUM(pable) AS pable
                    ,SUM(ped) AS ped
                    ,SUM(init) + SUM(pable) - SUM(ped) AS balance
                FROM
                    (
                        SELECT
                                org_unit
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
                                org_unit
                                ,party
                                ,0 AS init
                                ,SUM(amount) AS pable
                                ,0 AS ped
                            FROM
                                current_account
                            WHERE
                                stereo = 'PABLE' AND_VERIFIED
                            GROUP BY
                                org_unit
                                ,party
                        UNION ALL
                        SELECT
                                org_unit
                                ,party
                                ,0 AS init
                                ,0 AS pable
                                ,- SUM(amount) AS ped
                            FROM
                                current_account
                            WHERE
                                stereo = 'PED' AND_VERIFIED
                            GROUP BY
                                org_unit
                                ,party
                    ) a1
                GROUP BY
                    org_unit
                    ,party
        ) a2
            LEFT JOIN org_unit p
                ON a2.org_unit = p.id
            LEFT JOIN party b
                ON a2.party = b.id;
