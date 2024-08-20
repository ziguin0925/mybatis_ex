SELECT * FROM web_ex.product;
insert into brand(brand_id, name, img, product_num)
	values
		('A00000000001', 'GUCCI', '/img/GUCCI/brand/img1',50),
		('A00000000002', 'NIKE', '/img/NIKE/brand/img1',120),
		('A00000000003', 'ADIDAS', '/img/ADIDAS/brand/img1',361),
		('A00000000004', 'MNC', '/img/MNC/brand/img1',28),
        ('B00000000001', 'PRADA', '/img/PRADA/brand/img1',95),
        ('B00000000002', 'UNIQLO', '/img/UNIQLO/brand/img1',546),
        ('B00000000003', 'D&G', '/img/D&G/brand/img1',112),
        ('B00000000004', 'POLO', '/img/POLO/brand/img1',42),
        ('B00000000005', 'CHANEL', '/img/CHANEL/brand/img1', 78),
		('B00000000006', 'BURBERRY', '/img/BURBERRY/brand/img1', 105)
        
;
insert into product_description(product_description_id, description, modify_datetime)
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


INSERT INTO category (category_id, category_name, parent_category_id) 
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


INSERT INTO product (
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
	('P001', 'NIKE000000001', 'C05', 'A00000000002', '나이키 남성 빨강 티셔츠', '/img/NIKE/rep/image1.jpg', 29900, 0,'SELL', '2021-08-05 12:00:00', NOW(), 120, 'manager1', 45, 0, 0, 0),
    ('P002', 'GUCCI00000002', 'C08', 'A00000000001', '구찌 여성 스몰 사이즈 블라우스', '/img/GUCCI/rep/image1.jpg', 248900, 0, 'SELL', '2022-08-01 07:30:00', NOW(), 55550, 'manager2', 48, 0, 0, 0),
    ('P003', 'ADIDAS0000005', 'C11', 'A00000000003', '아디다스 아동 티셔츠 1', '/img/ADIDAS/rep/image1.jpg', 58900, 0,'SELL', '2012-07-15 11:30:00', NOW(), 28839, 'manager3', 49, 0, 0, 0),
    ('P004', 'ADIDAS0000005', 'C11', 'A00000000003', '아디다스 아동 티셔츠 2', '/img/ADIDAS/rep/image2.jpg', 58900, 0,'SELL', '2021-01-09 11:30:00', NOW(), 1267, 'manager3', 40, 0, 0, 0),
    ('P005', 'MNC0000000003', 'C06', 'A00000000004', 'MNC 남성 청바지', '/img/MNC/rep/image1.jpg', 19900, 0,'SELL', '2023-11-09 22:21:19', NOW(), 15425, 'manager4', 39, 0, 0, 0), 
    ('P006', 'NIKE000000002', 'C09', 'A00000000002', '나이키 여성 스커트', '/img/NIKE/rep/image2.jpg', 15260, 0,'SELL', '2024-02-01 09:41:12', NOW(), 67890, 'manager5', 40, 0, 0, 0),
    ('P007', 'GUCCI00000001', 'C13', 'A00000000001', '구찌 아동 아우터 특가 상품', '/img/GUCCI/rep/image2.jpg', 78900, 0,'SELL', '2024-03-11 19:01:10', NOW(), 1403, 'manager2', 41, 0, 0, 0),
    ('P008', 'NIKE000000002', 'C08', 'A00000000002', '나이키 여성 블라우스 특가 상품', '/img/NIKE/rep/image3.jpg', 89000, 0,'SELL', '2023-12-01 10:34:19', NOW(), 5123, 'manager1', 43, 0, 0, 0),
    ('P009', 'NIKE000000002', 'C08', 'A00000000002', '나이키 여성 블라우스 특가 상품', '/img/NIKE/rep/image23.jpg', 89000, 0,'SELL', '2022-12-09 11:11:11', NOW(), 1123, 'manager3', 45, 0, 0, 0),
    ('P010', 'CHANEL00000001', 'C55', 'B00000000005', '샤넬 여성 핸드백', '/img/CHANEL/rep/image1.jpg', 1290000, 0,'SELL', '2024-08-01 12:00:00', NOW(), 320, 'manager6', 47, 0, 0, 0),
    ('P011', 'BURBERRY00000001', 'C57', 'B00000000006', '버버리 남성 코트', '/img/BURBERRY/rep/image1.jpg', 599000, 0,'SELL', '2024-08-15 10:00:00', NOW(), 210, 'manager7', 45, 0, 0, 0),
    ('P018', 'NIKE000000007', 'C17', 'A00000000002', '나이키 남성 티셔츠 3', '/img/NIKE/rep/image10.jpg', 29900, 0,'SELL', '2024-08-20 12:00:00', NOW(), 850, 'manager11', 46, 0, 0, 0),
    ('P019', 'NIKE000000007', 'C17', 'A00000000002', '나이키 남성 티셔츠 4', '/img/NIKE/rep/image11.jpg', 31900, 0,'SELL', '2024-08-21 12:00:00', NOW(), 1100, 'manager11', 47, 0, 0, 0),
    ('P020', 'NIKE000000008', 'C17', 'A00000000002', '나이키 남성 티셔츠 5', '/img/NIKE/rep/image12.jpg', 28900, 0,'SELL', '2024-08-22 12:00:00', NOW(), 950, 'manager12', 45, 0, 0, 0),
    ('P021', 'NIKE000000008', 'C17', 'A00000000002', '나이키 남성 티셔츠 6', '/img/NIKE/rep/image13.jpg', 29900, 0,'SELL', '2024-08-23 12:00:00', NOW(), 1200, 'manager12', 46, 0, 0, 0),
    ('P022', 'NIKE000000009', 'C17', 'A00000000002', '나이키 남성 스포츠 티셔츠 1', '/img/NIKE/rep/image14.jpg', 32900, 0,'SELL', '2024-08-24 12:00:00', NOW(), 1300, 'manager13', 47, 0, 0, 0),
    ('P023', 'NIKE000000009', 'C17', 'A00000000002', '나이키 남성 스포츠 티셔츠 2', '/img/NIKE/rep/image15.jpg', 33900, 0,'SELL', '2024-08-25 12:00:00', NOW(), 1400, 'manager13', 48, 0, 0, 0),
    ('P024', 'NIKE000000010', 'C17', 'A00000000002', '나이키 남성 클래식 티셔츠 1', '/img/NIKE/rep/image16.jpg', 25900, 0,'SELL', '2024-08-26 12:00:00', NOW(), 1500, 'manager14', 44, 0, 0, 0),
    ('P025', 'NIKE000000010', 'C17', 'A00000000002', '나이키 남성 클래식 티셔츠 2', '/img/NIKE/rep/image17.jpg', 26900, 0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0),
    ('P027', 'NIKE000000010', 'C17', 'A00000000002', '나이키 남성 클래식 티셔츠 5', '/img/NIKE/rep/image27.jpg', 26900,  0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0),
	('P026', 'NIKE000000010', 'C17', 'A00000000002', '나이키 남성 티셔츠 9', '/img/NIKE/rep/image272.jpg', 26900, 0,'SELL', '2024-08-27 12:00:00', NOW(), 1600, 'manager14', 45, 0, 0, 0)
;

        
INSERT INTO stock(product_id, size, color, quantity, create_datetime)
	VALUES
		('P001','L','R', 123,NOW()),
        ('P001','XL','R', 42,NOW()),
        ('P001','M','R',13 ,NOW()),
        ('P002','S','Blue',451 ,NOW()),
        ('P002','S','Red',111 ,NOW()),
        ('P002','S','Gray',200 ,NOW()),
        ('P002','S','Green',345 ,NOW()),
        ('P002','S','Black',512 ,NOW()),
        ('P003','M','Blue',451 ,NOW()),
        ('P003','L','Blue',43 ,NOW()),
        ('P003','XL','Blue',45 ,NOW()),
		('P003','XXL','Blue',41 ,NOW()),
		('P003','M','Green',1452 ,NOW()),
        ('P003','L','Green',34 ,NOW()),
        ('P003','XL','Green',2 ,NOW()),
		('P003','XXL','Green',411 ,NOW()),
        ('P004','L','Gray',2 ,NOW()),
        ('P005','28','blue',123 ,NOW()),
        ('P005','30','blue',142 ,NOW()),
        ('P005','32','blue',231 ,NOW()),
        ('P005','34','blue',23 ,NOW()),
        ('P005','36','blue',12 ,NOW()),
        ('P006','24','White',250 ,NOW()),
        ('P006','26','White',123 ,NOW()),
        ('P006','28','White',244 ,NOW()),
        ('P006','30','White',119 ,NOW()),
        ('P007','XL','black',189 ,NOW()),
        ('P007','L','black',123 ,NOW()),
        ('P007','M','black',111 ,NOW()),
        ('P007','S','black',321 ,NOW()),
        ('P008','24','blue',32 ,NOW()),
        ('P008','26','blue',45 ,NOW()),
        ('P008','28','blue',67 ,NOW()),
        ('P008','30','blue',213 ,NOW()),
        ('P010', 'M', 'Black', 50, NOW()),
		('P010', 'L', 'Black', 30, NOW()),
		('P010', 'M', 'Beige', 20, NOW()),
		('P011', 'M', 'Navy', 40, NOW()),
		('P011', 'L', 'Navy', 25, NOW()),
		('P011', 'M', 'Gray', 15, NOW()),
        ('P018', 'M', 'White', 200, NOW()),
		('P018', 'L', 'White', 150, NOW()),
		('P018', 'XL', 'White', 100, NOW()),
		('P019', 'M', 'Black', 180, NOW()),
		('P019', 'L', 'Black', 160, NOW()),
		('P019', 'XL', 'Black', 140, NOW()),
		('P020', 'M', 'Gray', 220, NOW()),
		('P020', 'L', 'Gray', 180, NOW()),
		('P020', 'XL', 'Gray', 140, NOW()),
		('P021', 'M', 'Navy', 200, NOW()),
		('P021', 'L', 'Navy', 170, NOW()),
		('P021', 'XL', 'Navy', 130, NOW()),
		('P022', 'M', 'Red', 150, NOW()),
		('P022', 'L', 'Red', 120, NOW()),
		('P022', 'XL', 'Red', 100, NOW()),
		('P023', 'M', 'Blue', 180, NOW()),
		('P023', 'L', 'Blue', 150, NOW()),
		('P023', 'XL', 'Blue', 120, NOW()),
		('P024', 'M', 'Green', 210, NOW()),
		('P024', 'L', 'Green', 190, NOW()),
		('P024', 'XL', 'Green', 160, NOW()),
		('P025', 'M', 'Orange', 180, NOW()),
		('P025', 'L', 'Orange', 160, NOW()),
		('P025', 'XL', 'Orange', 140, NOW())
;

INSERT INTO product_description_img (
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

