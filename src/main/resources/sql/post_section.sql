    create table tbl_post_section
    (
        id           bigint generated always as identity primary key,
        post_content text,
        post_id      bigint not null,
        constraint fk_post_section_post foreign key (post_id)
            references tbl_post (id)
    );

