create table tbl_crew
(
    id                bigint generated always as identity primary key,
    crew_name         varchar(255) not null,
    crew_description  varchar(255) not null,
    crew_member_count int          not null,
    created_datetime  timestamp default now(),
    updated_datetime  timestamp default now()
);
