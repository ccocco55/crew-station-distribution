create table tbl_crew_file
(
    file_id bigint primary key,
    crew_id bigint not null,
    constraint fk_crew_file_crew foreign key (crew_id)
        references tbl_crew (id),
    constraint fk_crew_file_file foreign key (file_id)
        references tbl_file (id)
);