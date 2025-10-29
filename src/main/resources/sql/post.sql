create table tbl_post
(
    id               bigint generated always as identity primary key,
    post_title       varchar(255) not null,
    post_read_count  int       default 0,
    post_status      status    default 'active',
    member_id        bigint       not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_post_member foreign key (member_id)
        references tbl_member (id)
);