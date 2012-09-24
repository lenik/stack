SELECT
    a.person,
    b.label AS person_name,
    c.label AS ca,
    a.money
FROM (
    SELECT
        person,
        party,
        sum(native_value) as money
    FROM account_ticket_item
    WHERE
        NOT (party IS null) AND_VERIFIED
    GROUP BY person,party
) a
LEFT JOIN party b
    ON a.person=b.id
LEFT JOIN party c
    ON a.party=c.id
ORDER BY a.person;
