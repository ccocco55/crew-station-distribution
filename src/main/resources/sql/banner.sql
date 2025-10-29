create table tbl_banner
(
    id               bigint generated always as identity primary key,
    banner_order     int not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now()
);

insert into tbl_banner (banner_order)
values (5);

select *
from tbl_banner;