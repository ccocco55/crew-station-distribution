create table tbl_inquiry_reply
(
    id                    bigint generated always as identity primary key,
    inquiry_reply_content varchar(255) not null,
    member_id             bigint       not null,
    inquiry_id            bigint       not null,
    created_datetime      timestamp default now(),
    updated_datetime      timestamp default now(),
    constraint fk_inquiry_reply_member foreign key (member_id)
        references tbl_member (id),
    constraint fk_inquiry_reply_inquiry foreign key (inquiry_id)
        references tbl_inquiry (id)
);