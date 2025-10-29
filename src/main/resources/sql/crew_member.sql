create table tbl_crew_member
(
    id               bigint generated always as identity primary key,
    crew_role        crew_role default 'partner',
    crew_id          bigint not null,
    member_id        bigint not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_crew_member_crew foreign key (crew_id)
        references tbl_crew (id),
    constraint fk_crew_member_member foreign key (member_id)
        references tbl_member (id)
);