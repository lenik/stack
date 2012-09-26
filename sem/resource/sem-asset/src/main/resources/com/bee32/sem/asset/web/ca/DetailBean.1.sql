SELECT
    aa.begin_time,
    bb.label AS party,
    aa.label,
    cc.label AS subject,
    aa.money1,
    aa.money2,
    aa.balance
FROM (

    SELECT
        begin_time,
        party,
        label,
        subject,
        native_value AS money1,
        0.0 AS money2,
        0.0 AS balance
    FROM account_ticket_item
    WHERE NOT (party IS null) AND native_value>0 AND_VERIFIED

    UNION ALL

    SELECT
        begin_time,
        party,
        label,
        subject,
        0.0 AS money1,
        -native_value AS money2,
        0.0 AS balance
    FROM account_ticket_item
    WHERE NOT (party IS null) and native_value<0 AND_VERIFIED
) aa
LEFT JOIN party bb
    ON aa.party=bb.id
LEFT JOIN account_subject cc
    ON aa.subject=cc.id
ORDER BY aa.party,aa.subject,aa.begin_time;