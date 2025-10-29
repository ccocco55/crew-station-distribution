create table tbl_crew_diary
(
    id       bigint generated always as identity primary key,
    crew_id  bigint not null,
    diary_id bigint not null,
    constraint fk_crew_diary_crew foreign key (crew_id)
        references tbl_crew (id),
    constraint fk_crew_diary_diary foreign key (diary_id)
        references tbl_diary (post_id)
);