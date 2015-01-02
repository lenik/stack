
    -- Check item/ticket/fund_flow labels.
        select a.id, a.description, a.subject, c.label, 
                a.person, p.label "person_label",
                a.debit_side, a.value,
                a.ticket, -- t.label "ticket_label", t.description "ticket_description",
                f.id "fflow_id", f.description "fflow_descr"
            from old.account_ticket_item a
                left join old.account_subject c on a.subject=c.id
                left join old.party p on a.person=p.id
                left join old.account_ticket t on a.ticket=t.id
                left join old.fund_flow f on a.ticket=f.ticket
                left join old.principal u on a.owner=u.id
            -- where a.ticket in (select distinct ticket from old.account_ticket_item where value<0)
            order by a.ticket;

