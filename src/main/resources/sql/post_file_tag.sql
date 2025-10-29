create table tbl_post_file_tag
(
    id                   bigint generated always as identity primary key,
    tag_left             real,
    tag_top              real,
    member_id            bigint not null,
    post_section_file_id bigint not null,
    created_datetime     timestamp default now(),
    updated_datetime     timestamp default now(),
    constraint fk_post_file_tag_member foreign key (member_id)
        references tbl_member (id),
    constraint fk_post_file_tag_post_file foreign key (post_section_file_id)
        references tbl_post_section_file (file_id)
);