create table tbl_file
(
    id               bigint generated always as identity primary key,
    file_origin_name varchar(255) not null,
    file_path        varchar(255) not null,
    file_name        varchar(255) not null,
    file_size        varchar(255) not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now()
);

insert into tbl_file (file_origin_name, file_path, file_name, file_size)
values ('1','../../','1','1');