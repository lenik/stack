SELECT
    a.org_unit,
    b.label AS orgUnit_name,
    c.label AS ca,
    a.money
FROM (
    SELECT
        org_unit,
        party,
        sum(native_value) as money
    FROM account_ticket_item
    WHERE
        NOT (party IS null) AND_VERIFIED
    GROUP BY org_unit,party
) a
LEFT JOIN org_unit b
    ON a.org_unit=b.id
LEFT JOIN party c
    ON a.party=c.id
ORDER BY a.org_unit;
