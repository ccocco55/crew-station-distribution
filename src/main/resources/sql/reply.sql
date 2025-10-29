create table tbl_reply
(
    id               bigint generated always as identity primary key,
    reply_content    varchar(255) not null,
    diary_id         bigint       not null,
    member_id        bigint       not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_reply_member foreign key (member_id)
        references tbl_member (id),
    constraint fk_reply_diary foreign key (diary_id)
        references tbl_diary (post_id)
);


ALTER TABLE tbl_reply
    ADD COLUMN reply_status status NOT NULL DEFAULT 'active';