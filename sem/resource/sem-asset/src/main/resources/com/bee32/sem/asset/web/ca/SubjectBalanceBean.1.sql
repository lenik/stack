SELECT
    a.subject,
    b.label AS subject_name,
    c.label AS ca,
    a.money
FROM (
    SELECT
        subject,
        party,
        sum(native_value) as money
    FROM account_ticket_item
    WHERE
        1=1 VERIFIED
    GROUP BY subject,party
) a
LEFT JOIN account_subject b
    ON a.subject=b.id
LEFT JOIN party c
    ON a.party=c.id
ORDER BY a.subject;
