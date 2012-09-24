SELECT
    c.label AS ca,
    a.money
FROM (
    SELECT
        party,
        sum(native_value) as money
    FROM account_ticket_item
    WHERE
        NOT (party IS null) AND_VERIFIED
    GROUP BY party
) a
LEFT JOIN party c
    ON a.party=c.id
ORDER BY c.label;
