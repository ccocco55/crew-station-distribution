create table tbl_guest
(
    id bigint generated always as identity primary key,
    member_id bigint not null,
    guest_phone varchar(255) not null,
    guest_password varchar(255) not null,
    guest_order_number varchar(255) not null,
    address_zip_code char(5)      not null,
    address_detail   varchar(255) not null,
    address          varchar(255) not null,
    constraint fk_guest_member foreign key (member_id)
        references tbl_member (id)
);


alter table tbl_guest
    add column  created_datetime timestamp       default now();
alter table tbl_guest
    add column  updated_datetime timestamp       default now();
alter table tbl_guest
    add column  guest_password varchar(255) not null default '';

ALTER TABLE tbl_guest
    ADD CONSTRAINT uq_guest_order_number UNIQUE (guest_order_number);