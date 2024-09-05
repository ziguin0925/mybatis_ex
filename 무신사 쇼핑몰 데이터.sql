
insert ignore into brand(brand_id, name, img, product_num)
	values
		('GUCCI', '구찌', 'brandProducts/GUCCI/GUCCIimg.jpg',50),
		('NIKE', '나이키', 'brandProducts/NIKE/NIKEimg.jpg',120),
		('ADIDAS', '아디다스', 'brandProducts/ADIDAS/ADIDASimg.jpg',361),
		('MNC', '엠엔씨', 'brandProducts/MNC/MNCimg.jpg',28),
        ('PRADA', '프라다', 'brandProducts/PRADA/PARADAimg.png',95),
        ('UNIQLO', '유니클로', 'brandProducts/UNIQLO/UNIQLOimg.png',546),
        ('D&G', '돌체앤 가바나', 'brandProducts/D&G/D&Gimg.png',112),
        ('POLO', '폴로', 'brandProducts/POLO/POLOimg.png',42),
        ('CHANEL', '샤넬', 'brandProducts/CHANEL/CHANELimg.jpg', 78),
		('BURBERRY', '버버리', 'brandProducts/BURBERRY/BURBERRYimg.jpg', 105)
;

insert  ignore into product_description(product_description_id, description, modify_datetime)
	values
		('NIKE000000001', '나이키의 반팔 티는 좋은 제품이다.', '2019-05-22'),
        ('NIKE000000007', '나이키 남성 티셔츠는 통기성이 뛰어나며 편안한 착용감을 제공합니다.', '2024-08-20 00:00:00'),
		('NIKE000000008', '나이키 남성 티셔츠는 스타일과 기능성을 모두 갖춘 필수 아이템입니다.', '2024-08-21 00:00:00'),
		('NIKE000000009', '나이키 남성 스포츠 티셔츠는 활동적인 라이프스타일에 적합합니다.', '2024-08-22 00:00:00'),
		('NIKE000000010', '나이키 남성 클래식 티셔츠는 다양한 색상으로 제공되며, 일상적인 착용에 적합합니다.', '2024-08-23 00:00:00'),
        ('GUCCI00000001', '구찌의 아동 복은 좋은 제품이다.', '2024-03-11'),
		('GUCCI00000002', '구찌의 여성 블라우스는 좋은 제품이다.', '2024-01-03'),
		('MNC0000000003', 'MNC의 모자는 좋은 제품이다.', '2023-09-24'),
		('ADIDAS0000005', '아디다스의 아동복은 좋은 제품이다.', '2011-11-24'),
        ('NIKE000000002', '나이키의 여성 스커트는 좋은 제품이다.', '2021-05-12'),
        ('ADIDAS0000001', '아디다스의 여성 블라우스는 좋은 제품이다.', '2011-10-24'),
        ('POLO000000001', '폴로의 남성 티셔츠는 좋은 제품이다.', '2011-09-11'),
        ('CHANEL00000001', '샤넬의 여성 핸드백은 고급스럽고 실용적이다.', '2024-07-01 00:00:00'),
		('BURBERRY00000001', '버버리의 남성 코트는 스타일리시하고 따뜻하다.', '2024-07-15 00:00:00')
;


INSERT ignore  INTO category (category_id, category_name, parent_category_id) 
	VALUES

		('C01', '의류', NULL),
        
        
        ('C02', '남성 의류', 'C01'),
		('C03', '여성 의류', 'C01'),
		('C04', '아동 의류', 'C01'),
        ('C05', '남성 상의', 'C02'),
		('C06', '남성 하의', 'C02'),
		('C07', '남성 아우터', 'C02'),
        ('C08', '여성 상의', 'C03'),
		('C09', '여성 하의', 'C03'),
		('C10', '여성 아우터', 'C03'),
        ('C11', '아동 상의', 'C04'),
		('C12', '아동 하의', 'C04'),
		('C13', '아동 아우터', 'C04'),


		-- 남성 의류 세부 카테고리
		('C14', '남성 액세서리', 'C02'),
		('C15', '남성 신발', 'C02'),
		('C16', '남성 스포츠 의류', 'C02'),
        
		-- 남성 상의 세부 카테고리
		('C17', '남성 티셔츠', 'C05'),
		('C18', '남성 셔츠', 'C05'),
		('C19', '남성 스웨터', 'C05'),
		('C20', '남성 조끼', 'C05'),
        
		-- 남성 하의 세부 카테고리
		('C21', '남성 청바지', 'C06'),
		('C22', '남성 반바지', 'C06'),
		('C23', '남성 슬랙스', 'C06'),
        
		-- 남성 아우터 세부 카테고리
		('C24', '남성 자켓', 'C07'),
		('C25', '남성 코트', 'C07'),
		('C26', '남성 패딩', 'C07'),
        
		-- 여성 의류 세부 카테고리
		('C27', '여성 액세서리', 'C03'),
		('C28', '여성 신발', 'C03'),
		('C29', '여성 스포츠 의류', 'C03'),
        
		-- 여성 상의 세부 카테고리
		('C30', '여성 블라우스', 'C08'),
		('C31', '여성 티셔츠', 'C08'),
		('C32', '여성 스웨터', 'C08'),
		('C33', '여성 카디건', 'C08'),
        
		-- 여성 하의 세부 카테고리
		('C34', '여성 스커트', 'C09'),
		('C35', '여성 청바지', 'C09'),
		('C36', '여성 슬랙스', 'C09'),
        
		-- 여성 아우터 세부 카테고리
		('C37', '여성 자켓', 'C10'),
		('C38', '여성 코트', 'C10'),
		('C39', '여성 패딩', 'C10'),
        
		-- 아동 의류 세부 카테고리
		('C40', '아동 액세서리', 'C04'),
		('C41', '아동 신발', 'C04'),
		('C42', '아동 스포츠 의류', 'C04'),
        
		-- 아동 상의 세부 카테고리
		('C43', '아동 티셔츠', 'C11'),
		('C44', '아동 셔츠', 'C11'),
		('C45', '아동 스웨터', 'C11'),
        
		-- 아동 하의 세부 카테고리
		('C46', '아동 청바지', 'C12'),
		('C47', '아동 반바지', 'C12'),
		('C48', '아동 레깅스', 'C12'),
        
		-- 아동 아우터 세부 카테고리
		('C49', '아동 자켓', 'C13'),
		('C50', '아동 코트', 'C13'),
		('C51', '아동 패딩', 'C13'),
        
        ('C52', '액세서리', 'C01'),
		('C53', '가방', 'C01'),
		('C54', '신발', 'C01'),
		('C55', '남성 가방', 'C53'),
		('C56', '여성 가방', 'C53'),
		('C57', '남성 신발', 'C54'),
		('C58', '여성 신발', 'C54')
;


INSERT ignore INTO product (
    product_id
    , product_description_id
    , category_id
    , brand_id
    , name
    , rep_img
    , price
    , event_price
    , product_status
    , create_datetime
    , modify_datetime
    , sales_quantity
    , register_manager
    , star_rating
    , view_count
    , review_count
    , like_count
) VALUES
	('P001', 'NIKE000000001', 'C05', 'NIKE', '나이키 남성 빨강 티셔츠', 'brandProducts/NIKE/productRepImg/P001/NikeRed.jpg', 29900, 0,'SELL', '2021-08-05 12:00:00', NOW(), 120, 'manager1', 45, 0, 0, 0),
    ('P002', 'GUCCI00000002', 'C08', 'GUCCI', '구찌 여성 스몰 사이즈 블라우스', 'brandProducts/GUCCI/productRepImg/P002/구찌여성블라우스.jpg', 248900, 0, 'SELL', '2022-08-01 07:30:00', NOW(), 55550, 'manager2', 48, 0, 0, 0),
    ('P003', 'ADIDAS0000005', 'C11', 'ADIDAS', '아디다스 아동 티셔츠 1', 'brandProducts/ADIDAS/productRepImg/P003/qwtid-iej아디다스아동 티셔츠.jpg', 58900, 0,'SELL', '2012-07-15 11:30:00', NOW(), 28839, 'manager3', 49, 0, 0, 0),
    ('P004', 'ADIDAS0000005', 'C11', 'ADIDAS', '아디다스 아동 티셔츠 2', 'brandProducts/ADIDAS/productRepImg/P004/ADIDAS티셔츠.jpg', 58900, 0,'SELL', '2021-01-09 11:30:00', NOW(), 1267, 'manager3', 40, 0, 0, 0),
    ('P005', 'MNC0000000003', 'C06', 'MNC', 'MNC 남성 청바지', 'brandProducts/MNC/productRepImg/P005/MNCjeans.jpg', 19900, 0,'SELL', '2023-11-09 22:21:19', NOW(), 15425, 'manager4', 39, 0, 0, 0), 
    ('P006', 'NIKE000000002', 'C09', 'NIKE', '나이키 여성 스커트', 'brandProducts/NIKE/productRepImg/P006/NIKE스커트.jpg', 15260, 0,'SELL', '2024-02-01 09:41:12', NOW(), 67890, 'manager5', 40, 0, 0, 0),
    ('P007', 'GUCCI00000001', 'C13', 'GUCCI', '구찌 아동 아우터 특가 상품', 'brandProducts/GUCCI/productRepImg/P007/gucciouter.jpg', 78900, 0,'SELL', '2024-03-11 19:01:10', NOW(), 1403, 'manager2', 41, 0, 0, 0),
    ('P008', 'NIKE000000002', 'C08', 'NIKE', '나이키 여성 블라우스 특가 상품', 'brandProducts/NIKE/productRepImg/P008/nike블라우스1.jpg', 89000, 0,'SELL', '2023-12-01 10:34:19', NOW(), 5123, 'manager1', 43, 0, 0, 0),
    ('P009', 'NIKE000000002', 'C08', 'NIKE', '나이키 여성 블라우스 특가 상품', 'brandProducts/NIKE/productRepImg/P009/나이키여성 블라우스.jpg', 89000, 0,'SELL', '2022-12-09 11:11:11', NOW(), 1123, 'manager3', 45, 0, 0, 0),
    ('P010', 'CHANEL00000001', 'C55', 'CHANEL', '샤넬 여성 핸드백', 'brandProducts/CHANEL/productRepImg/P010/chanel handbag.jpg', 1290000, 0,'SELL', '2024-08-01 12:00:00', NOW(), 320, 'manager6', 47, 0, 0, 0),
    ('P011', 'BURBERRY00000001', 'C57', 'BURBERRY', '버버리 남성 코트', 'brandProducts/BURBERRY/productRepImg/P011/BURBERRYcoat.jpg', 599000, 0,'SELL', '2024-08-15 10:00:00', NOW(), 210, 'manager7', 45, 0, 0, 0),
    ('P018', 'NIKE000000007', 'C17', 'NIKE', '나이키 남성 티셔츠 3', 'brandProducts/NIKE/productRepImg/P018/나이키 티셔츠.jpg', 29900, 0,'SELL', '2024-08-20 12:00:00', NOW(), 850, 'manager11', 46, 0, 0, 0),
    ('P019', 'NIKE000000007', 'C17', 'NIKE', '나이키 남성 티셔츠 4', 'brandProducts/NIKE/productRepImg/P019/나이키 티셔츠파랑.jpg', 31900, 0,'SELL', '2024-08-21 12:00:00', NOW(), 1100, 'manager11', 47, 0, 0, 0),
    ('P020', 'NIKE000000008', 'C17', 'NIKE', '나이키 남성 티셔츠 5', 'brandProducts/NIKE/productRepImg/P020/나이키 티셔츠.jpg', 28900, 0,'SELL', '2024-08-22 12:00:00', NOW(), 950, 'manager12', 45, 0, 0, 0),
    ('P021', 'NIKE000000008', 'C17', 'NIKE', '나이키 남성 티셔츠 6', 'brandProducts/NIKE/productRepImg/P021/나이키 스투시.jpg', 29900, 0,'SELL', '2024-08-23 12:00:00', NOW(), 1200, 'manager12', 46, 0, 0, 0),
    ('P022', 'NIKE000000009', 'C17', 'NIKE', '나이키 남성 스포츠 티셔츠 1', 'brandProducts/NIKE/productRepImg/P022/스포츠 티셔츠.jpg', 32900, 0,'SELL', '2024-08-24 12:00:00', NOW(), 1300, 'manager13', 47, 0, 0, 0),
    ('P023', 'NIKE000000009', 'C17', 'NIKE', '나이키 남성 스포츠 티셔츠 2', 'brandProducts/NIKE/productRepImg/P023/nikesport.jpg', 33900, 0,'SELL', '2024-08-25 12:00:00', NOW(), 1400, 'manager13', 48, 0, 0, 0),
    ('P024', 'NIKE000000010', 'C17', 'NIKE', '나이키 남성 클래식 티셔츠 1', 'brandProducts/NIKE/productRepImg/P024/nikesports3.jpg', 25900, 0,'SELL', '2024-08-26 12:00:00', NOW(), 1500, 'manager14', 44, 0, 0, 0),
    ('P025', 'NIKE000000010', 'C17', 'NIKE', '나이키 남성 클래식 티셔츠 2', 'brandProducts/NIKE/productRepImg/P024/nikesports3.jpg', 26900, 0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0),
    ('P027', 'NIKE000000010', 'C17', 'NIKE', '나이키 남성 클래식 티셔츠 5', 'brandProducts/NIKE/productRepImg/P027/나이키 긴팔티.jpg', 26900,  0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0),
	('P026', 'NIKE000000010', 'C17', 'NIKE', '나이키 남성 티셔츠 9', 'brandProducts/NIKE/productRepImg/P026/나이키 클래식 티셔츠.jpg', 26900, 0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0)
;

        

INSERT ignore INTO product_description_img (
    product_description_id
    , name
    , order_num
    , path
    , is_used
    , kind_of
    , size
    , create_datetime
    , modify_datetime
) VALUES

	('NIKE000000001', '반팔 티image_D1', 1, '/img/NIKE/image1.jpg','Y' , 'DES', 1024, NOW(), NOW()),
	('NIKE000000001', 'aaaimage_D2', 2, '/img/NIKE/image2.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000001', 'aaaimage_R1', 1, '/img/NIKE/image3.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	('NIKE000000001', 'aaaimage_R2', 2, '/img/NIKE/image4.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	
	
	('GUCCI00000001', '아동복 image_D1', 1, '/img/GUCCI/image1.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('GUCCI00000001', '아동복 image_D2', 1, '/img/GUCCI/image2.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('GUCCI00000001', '아동복 image_R1', 1, '/img/GUCCI/image3.jpg', 'Y', 'REP', 1024, NOW(), NOW()),
	
	
	('GUCCI00000002', '블라우스 image_R1', 1, '/img/GUCCI/image1.jpg', 'N', 'REP', 2048, NOW(), NOW()),
	('GUCCI00000002', '블라우스 image_R2', 2, '/img/GUCCI/image2.jpg', 'Y', 'REP', 1024, NOW(), NOW()),
	('GUCCI00000002', '블라우스 image_D1', 1, '/img/GUCCI/image4.jpg', 'Y', 'DES', 2048, NOW(), NOW()),
	('GUCCI00000002', '블라우스 image_D2', 2, '/img/GUCCI/image3.jpg', 'Y', 'DES', 512, NOW(), NOW()),
	
	
	('MNC0000000003', '청바지 image_D1', 2, '/img/MNC/image1.jpg', 'Y', 'DES', 512, NOW(), NOW()),
	('MNC0000000003', '청바지 image_D2', 1, '/img/MNC/image3.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('MNC0000000003', '청바지 image_R1', 1, '/img/MNC/image2.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	
	('ADIDAS0000005', 'adidas_image_R1', 2, '/img/ADIDAS/image1.jpg', 'Y', 'REP', 1024, NOW(), NOW()),
	('ADIDAS0000005', 'adidas_image_R2', 1, '/img/ADIDAS/image2.jpg', 'Y', 'REP', 1024, NOW(), NOW()),
	('ADIDAS0000005', 'adidas_image_D1', 1, '/img/ADIDAS/image288.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('ADIDAS0000005', 'adidas_image_D2', 3, '/img/ADIDAS/image284.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('ADIDAS0000005', 'adidas_image_D3', 2, '/img/ADIDAS/image215.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	
	('NIKE000000002', 'nike_블라우스_image_r1', 2, '/img/NIKE/image213.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	
	('CHANEL00000001', '핸드백 image_D1', 1, '/img/CHANEL/image1.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('CHANEL00000001', '핸드백 image_D2', 2, '/img/CHANEL/image2.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('BURBERRY00000001', '코트 image_D1', 1, '/img/BURBERRY/image1.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('BURBERRY00000001', '코트 image_D2', 2, '/img/BURBERRY/image2.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000007', '남성 티셔츠 image_D1', 1, '/img/NIKE/image18.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000007', '남성 티셔츠 image_D2', 2, '/img/NIKE/image19.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000007', '남성 티셔츠 image_R1', 1, '/img/NIKE/image20.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	('NIKE000000007', '남성 티셔츠 image_R2', 2, '/img/NIKE/image21.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
		
	('NIKE000000008', '남성 티셔츠 image_D1', 1, '/img/NIKE/image22.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000008', '남성 티셔츠 image_D2', 2, '/img/NIKE/image23.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000008', '남성 티셔츠 image_R1', 1, '/img/NIKE/image24.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	('NIKE000000008', '남성 티셔츠 image_R2', 2, '/img/NIKE/image25.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
		
	('NIKE000000009', '남성 스포츠 티셔츠 image_D1', 1, '/img/NIKE/image26.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000009', '남성 스포츠 티셔츠 image_D2', 2, '/img/NIKE/image27.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000009', '남성 스포츠 티셔츠 image_R1', 1, '/img/NIKE/image28.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	('NIKE000000009', '남성 스포츠 티셔츠 image_R2', 2, '/img/NIKE/image29.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
		
	('NIKE000000010', '남성 클래식 티셔츠 image_D1', 1, '/img/NIKE/image30.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000010', '남성 클래식 티셔츠 image_D2', 2, '/img/NIKE/image31.jpg', 'Y', 'DES', 1024, NOW(), NOW()),
	('NIKE000000010', '남성 클래식 티셔츠 image_R1', 1, '/img/NIKE/image32.jpg', 'Y', 'REP', 2048, NOW(), NOW()),
	('NIKE000000010', '남성 클래식 티셔츠 image_R2', 2, '/img/NIKE/image33.jpg', 'Y', 'REP', 2048, NOW(), NOW())
;

insert  ignore into product_option(product_id,product_option_id, option_category, option_name, option_depth)
	values 
    ('P002', 'A1', '사이즈', 'XL',1),
    ('P002', 'A2', '사이즈', 'L',1),
    ('P002', 'A1B1', '컬러', 'blue',2),
    ('P002', 'A1B2', '컬러', 'black',2)
    ;
INSERT ignore INTO stock(product_id, product_option_id,  quantity)
	VALUES
    ('P002','A1B2',200),
    ('P002','A1B1',199)
;


insert ignore into member_info(member_state_code, id, password, name, birth, sex, phone_number, email, login_datetime, modify_datetime, register_datetime, exit_datetime ,recommand_id, is_admin)
values
('40001', 'asdf1234', 'asdf1234!', '김진철', '2000-08-22', 'M', '01012345678', 'asdf@naver.com', now(), now() ,'2023-02-20 8:09:45', '9999-12-31 23:59:59', NULL, 'U' ),
('40001', 'qwer1234', 'qwer1234!', '우소미', '1999-09-22', 'F', '01009876543', 'qwer@naver.com', now(), now() ,'2023-04-21 8:09:35', '9999-12-31 23:59:59', 'asdf1234', 'U' ),
('40001', 'zxcv1234', 'zxcv1234!', '조시형', '1993-03-21', 'F', '01023456789', 'zxcv@naver.com', now(), now() ,'2023-05-03 16:04:00', '9999-12-31 23:59:59', NULL, 'U' ),
('40002', 'qwerty1234', 'qwerty1234!', '임미정', '1992-05-17', 'F', '01034567890', 'qwerty@naver.com', now(), now() , '2023-05-17 17:08:29', '9999-12-31 23:59:59', NULL, 'U' ),
('40001', 'asdfgh1234', 'asdfgh1234!', '공경민', '2000-02-20', 'M', '01045678901', 'asdfgh@naver.com', now(), now() ,'2023-06-25 8:00:00', '9999-12-31 23:59:59', NULL, 'U' ),
('40001', 'zxcvbn1234', 'zxcvbn1234!', '장우원', '1996-08-24', 'M', '01056789012', 'zxcvbn@naver.com', now(), now() ,'2023-07-09 14:23:45', '9999-12-31 23:59:59', 'asdf1234', 'U' ),
('40001', 'poiu1234', 'poiu1234!', '남라미', '2001-05-04', 'F', '01067890123', 'poiu@naver.com', now(), now() ,'2023-08-09 13:45:23', '9999-12-31 23:59:59', NULL, 'U' ),
('40001', 'lkjh1234', 'lkjh1234!', '최서윤', '2001-07-25', 'F', '01078901234', 'lkjh@naver.com', now(), now() ,'2023-09-21 16:24:17', '9999-12-31 23:59:59', NULL, 'U' ),
('40004', 'mnbv1234', 'mnbv1234!', '홍재안', '1995-04-14', 'M', '01089012345', 'mnbv@naver.com', now(), now() ,'2023-10-09 21:04:21', '2024-08-06 15:03:47', NULL, 'U' ),
('40001', 'ghjkl1234', 'ghjkl1234!', '이정헌', '1990-02-10', 'M', '01090123456', 'ghjkl@naver.com', now(), now() ,'2023-11-12 8:34:23', '9999-12-31 23:59:59', NULL, 'U' ),
('40001', '20230101001', '20230101001!', '문연지', '1992-11-24', 'F', '01001234567', 'yeonji@naver.com',now(), now() , '2023-12-01 8:00:00', '9999-12-31 23:59:59', NULL, 'A' ),
('40001', '20240101001', '20240101001!', '이아윤', '1994-11-14', 'F', '01087654321', 'ayoon@naver.com', now(), now() ,'2024-01-01 8:00:00', '9999-12-31 23:59:59', NULL, 'B' ),
('40001', '20240101002', '20240101002!', '윤모석', '1997-04-17', 'M', '01076543210', 'yoon@naver.com', now(), now() ,'2024-01-01 8:00:00', '9999-12-31 23:59:59', NULL, 'B' ),
('40001', '20240101003', '20240101003!', '오민승', '2002-09-28', 'F', '01065432109', 'ohmin@naver.com', now(), now() ,'2024-01-01 8:00:00', '9999-12-31 23:59:59', NULL, 'B' ),
('40001', 'jlwoo0925', 'jlwoo0925!', '정룡우', '2002-09-25', 'M', '01065432109', 'jlwoo092513@gmail.com', now(), now() ,'2024-01-01 8:00:00', '9999-12-31 23:59:59', NULL, 'B' )
;


insert ignore into admin_brand(id, brand_code)
values
('20240101001', 'GUCCI'),
('20240101002', 'NIKE'),
('20240101003', 'CHANEL'),
('jlwoo0925','MNC')
;

