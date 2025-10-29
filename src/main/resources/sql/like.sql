create table tbl_like
(
    id        bigint generated always as identity primary key,
    post_id   bigint not null,
    member_id bigint not null,
    constraint fk_like_post foreign key (post_id)
        references tbl_post (id),
    constraint fk_like_member foreign key (member_id)
        references tbl_member (id)
);
