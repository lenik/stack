select
    a.begin_time,a.end_time,c.label as customer,
    -a.amount as amount,a.bill_no,d.label as bill_type,a.accept_bank,
    e.label as accept_org,f.label as person
 from current_account a
left join note_balancing b
    on a.id=b.note
left join party c
    on a.party=c.id
left join bill_type d
    on a.bill_type=d.id
left join party e
    on a.accept_org=e.id
left join party f
    on a.person=f.id
where a.stereo='PNOTE' and b.id is null AND_VERIFIED