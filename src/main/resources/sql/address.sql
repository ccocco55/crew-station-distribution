create table tbl_address
(
    id               bigint generated always as identity primary key,
    address_zip_code char(5)      not null,
    address_detail   varchar(255) not null,
    address          varchar(255) not null,
    member_id        bigint,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_address_member foreign key (member_id)
        references tbl_member (id)
);
