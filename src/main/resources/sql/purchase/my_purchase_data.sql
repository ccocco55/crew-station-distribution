select * from tbl_post;
select * from view_post_purchase;
select * from tbl_member;
select * from view_post_purchase;


select * from tbl_payment_status;

INSERT INTO tbl_post (member_id, post_title, post_status, created_datetime, updated_datetime)
VALUES
    (2, '감성 머그컵', 'active', NOW(), NOW()),
    (3, '핸드메이드 키링', 'active', NOW(), NOW());

INSERT INTO tbl_payment_status (purchase_id, member_id, payment_phase, created_datetime, updated_datetime)
VALUES
    (15, 8, 'request', NOW(), NOW()),
    (16, 8, 'pending', NOW(), NOW());

INSERT INTO tbl_post_section (post_id)
VALUES
    (15),
    (16);

SELECT tps.payment_phase, tps.*, vpp.id
FROM tbl_payment_status tps
         JOIN view_post_purchase vpp ON tps.purchase_id = vpp.id
WHERE tps.member_id = 8;


TRUNCATE TABLE tbl_payment_status CASCADE;
SELECT id, member_name, member_phone FROM tbl_member WHERE id = 8;

SELECT * FROM tbl_payment_status WHERE id = 1;





