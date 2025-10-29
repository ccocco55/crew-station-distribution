select * from tbl_member;
select * from tbl_post;
select * from tbl_diary;
select * from tbl_like;
select * from tbl_diary_country_path;
select * from tbl_country;
select * from tbl_like;
select * from tbl_payment_status;


insert into tbl_post(post_title,member_id)
values ('테스트용 일기1',1);

insert into tbl_diary(post_id, diary_country_path_id)
values (2,1);

insert into tbl_diary_country_path(country_start_date, country_end_date, member_id, country_id)
values ('20250401','20250607',1,2);

insert into tbl_like(post_id,member_id)
values (2,3);

