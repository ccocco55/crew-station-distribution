create table tbl_payment_status
(
    id               bigint generated always as identity primary key,
    payment_phase    phase     default 'request',
    purchase_id      bigint not null,
    payment_id       bigint not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_payment_status_purchase foreign key (purchase_id)
        references tbl_purchase (post_id)

);

ALTER TABLE tbl_payment_status DROP COLUMN payment_id;

ALTER TABLE tbl_payment_status ADD COLUMN member_id bigint not null;


ALTER TABLE tbl_payment_status ADD CONSTRAINT fk_payment_status_member FOREIGN KEY (member_id) REFERENCES tbl_member (id);





