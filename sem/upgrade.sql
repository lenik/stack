alter table stock_order_item rename column batch to batch0;
alter table stock_order_item drop column cbatch;

alter table purchase_request_item drop column parent;
alter table purchase_request_item rename column purchase_request to parent;

alter table purchase_request_item rename column quantity to required_quantity;
alter table purchase_request_item rename column plan_quantity to quantity;

alter table purchase_inquiry drop column parent;
alter table purchase_inquiry rename column purchase_request_item to parent;
