create table tbl_accompany
(
    post_id             bigint primary key,
    accompany_status    accompany_status default 'short',
    accompany_age_range int not null,
    constraint fk_accompany_post foreign key (post_id)
        references tbl_post (id)
);
