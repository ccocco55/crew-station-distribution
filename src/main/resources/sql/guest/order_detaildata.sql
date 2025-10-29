-- 1. 판매자 회원 (member)
INSERT INTO tbl_member (member_name, member_phone, member_email)
VALUES ('홍길동', '01011112222', 'seller@test.com'),
('이몽룡', '01022223333', 'seller2@test.com'),
('성춘향', '01033334444', 'seller3@test.com');


-- 2. 구매자 회원 (member)
INSERT INTO tbl_member (member_name)
VALUES ('익명게스트1'),
       ('익명게스트2'),
       ('익명게스트3'),
       ('익명게스트4');

-- 2. 구매자 회원 (guest)
INSERT INTO tbl_guest (member_id, guest_phone, guest_order_number, address_zip_code, address_detail,address)
VALUES (4, '01099991111', '2025101509300011111', '04524', '101동 1101호', '서울시 중구 세종대로'),
       (5, '01099992222', '2025101509400022222', '06123', '202동 1505호', '서울시 강남구 테헤란로'),
       (6, '01099993333', '2025101509500033333', '21012', '301호', '부산시 해운대구 센텀서로'),
       (7, '01099994444', '2025101510000044444', '03011', '302동 808호', '서울시 종로구 율곡로');

-- 3. 게시글 (post)
INSERT INTO tbl_post (post_title, member_id)
VALUES ('테스트 러닝화', 1),
       ('스포츠 장갑', 2),
       ('캠핑용 텐트', 3),
       ('방수 자켓', 1),
       ('하이킹 백팩', 2);

-- 4. 구매설정 (purchase)
INSERT INTO tbl_purchase (post_id, purchase_product_price,purchase_limit_time, purchase_product_count, purchase_country, purchase_delivery_method)
VALUES (1, 12000, 10, 3, '대한민국', 'direct'),
       (2, 15000, 7, 5, '대한민국', 'parcel'),
       (3, 5000, 14, 2, '대한민국', 'direct'),
       (4, 9000, 5, 10, '대한민국', 'parcel'),
       (5, 3000, 9, 8, '대한민국', 'direct');

-- 6. 결제 상태 (payment_status)
INSERT INTO tbl_payment_status (payment_phase, purchase_id, member_id, created_datetime, updated_datetime)
VALUES ('pending', 1, 4, NOW(), NOW()),
       ('request', 2, 5, NOW(), NOW()),
       ('refund', 3, 6, NOW(), NOW()),
       ('received', 4, 7, NOW(), NOW()),
       ('success', 5, 8, NOW(), NOW());

-- 7. 파일 (file)
INSERT INTO tbl_file (file_origin_name, file_path, file_name, file_size)
VALUES ('gift-shop-shoes.jpg', '/images', 'gift-shop-shoes.png', '180KB'),
       ('gift-shop-gloves.jpg', '/images', 'gift-shop-gloves.png', '150KB'),
       ('gift-shop-tent.jpg', '/images', 'gift-shop-tent.png', '250KB'),
       ('gift-shop-jacket.jpg', '/images', 'gift-shop-jacket.png', '210KB'),
       ('gift-shop-bag.jpg', '/images', 'gift-shop-bag.png', '190KB');

-- 8. 게시글 섹션 (post_section)
INSERT INTO tbl_post_section (post_id)
VALUES (1), (2), (3), (4), (5);

-- 9. 게시글 섹션 파일 (post_section_file, 메인 이미지 연결)
INSERT INTO tbl_post_section_file (file_id, post_section_id, image_type)
VALUES (1, 1, 'main'),
       (2, 2, 'main'),
       (3, 3, 'main'),
       (4, 4, 'main'),
       (5, 5, 'main');

SELECT * FROM tbl_payment_status WHERE purchase_id = 1;


select * from tbl_payment_status;
select * from tbl_member;
select * from tbl_guest;

SELECT DISTINCT payment_phase FROM tbl_payment_status;


SELECT * FROM tbl_guest WHERE guest_order_number = '2025101710314156135';


TRUNCATE TABLE
    tbl_post_section_file,
    tbl_post_section,
    tbl_file,
    tbl_payment_status,
    tbl_payment,
    tbl_guest,
    tbl_purchase,
    tbl_post,
    tbl_member
    RESTART IDENTITY CASCADE;