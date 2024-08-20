create table if not exists brand(
	brand_id VARCHAR(25) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    img VARCHAR(500) ,
    product_num int  default 0 NOT NULL
);

create table if not exists product_description(
	product_description_id VARCHAR(25) PRIMARY KEY,
    description TEXT,
    modify_datetime DATETIME DEFAULT NOW() NOT NULL
);

create table if not exists category(
	category_id VARCHAR(3) PRIMARY KEY,
    category_name VARCHAR(10) NOT NULL,
    parent_category_id VARCHAR(3),
    CONSTRAINT category_fk
		FOREIGN KEY (parent_category_id)
		REFERENCES category(category_id)
);


create table if not exists product(
	product_id VARCHAR(25) PRIMARY KEY not null,
    product_description_id VARCHAR(25) not null,
    category_id VARCHAR(3) not null,
    brand_id VARCHAR(25) not null,
    name VARCHAR(50) NOT NULL,
    rep_img VARCHAR(500) NOT NULL,
    price INT NOT NULL,
    event_price INT default 0 NOT NULL,
    register_manager VARCHAR(20) NOT NULL,
    product_status VARCHAR(10) default 'SELL' NOT NULL ,
    create_datetime DATETIME DEFAULT NOW() NOT NULL ,
    modify_datetime DATETIME DEFAULT NOW() NOT NULL ,
    sales_quantity int default 0 NOT NULL,
    star_rating int default 0 NOT NULL, 
    view_count int default 0 NOT NULL,
    like_count int default 0 NOT NULL,
    review_count int default 0 NOT NULL,
    CONSTRAINT product_product_description_id_fk
        FOREIGN KEY (product_description_id)
        REFERENCES product_description (product_description_id),
	CONSTRAINT product_category_id_fk
        FOREIGN KEY (category_id)
        REFERENCES category(category_id),
	CONSTRAINT product_brand_id_fk
		FOREIGN KEY (brand_id)
        REFERENCES brand(brand_id)
        on delete cascade 
);


create table if not exists stock(
	size VARCHAR(5),
    color VARCHAR(10),
    product_id VARCHAR(20),
    quantity int NOT NULL,
    create_datetime DATETIME DEFAULT NOW() NOT NULL,
    constraint stock_tb primary key(product_id, color, size),
    CONSTRAINT stock_product_id_fk
		FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        on delete cascade 
);

create table if not exists product_description_img(
	product_description_img_id INT PRIMARY KEY auto_increment,
    product_description_id VARCHAR(25),
    name VARCHAR(50) NOT NULL,
    order_num tinyint NOT NULL,
    path VARCHAR(500) NOT NULL,
    is_used VARCHAR(1) default 'Y' NOT NULL,
    kind_of VARCHAR(10)  NOT NULL,
    size int  NOT NULL,
    create_datetime DATETIME DEFAULT NOW() NOT NULL,
    modify_datetime DATETIME DEFAULT NOW() NOT NULL,
	CONSTRAINT img_product_description_id_fk
        FOREIGN KEY (product_description_id)
        REFERENCES product_description(product_description_id)
        on delete cascade 
);



-- 해당 상품 카테고리
select c.category_name
	from product p 
	inner join category c 
    on p.category_id = c.category_id
    where p.product_id = 'P003';
    
    
    
-- 상위 모두 카테고리
with recursive CategoryHierarchy as (
    select
        category_id,
        category_name, 
        parent_category_id
    from
        category
    where
        category_id = 'C30'
    union all
    select
        c.category_id,
        c.category_name,
        c.parent_category_id
    from
        category c
    inner join
        CategoryHierarchy ch on c.category_id = ch.parent_category_id
)


select
    category_id,
    category_name
-- parent_category_id
from
    CategoryHierarchy;


/*
		하위 모든 카테고리
*/
explain WITH RECURSIVE SubCategories AS (
    SELECT
        category_id,
        category_name,
        parent_category_id
    FROM
        category
    WHERE
        category_id = 'C05'
    
    UNION ALL
    
    SELECT
        c.category_id,
        c.category_name,
        c.parent_category_id
    FROM
        category c
    INNER JOIN
        SubCategories sc
    ON
        c.parent_category_id = sc.category_id
)
SELECT
    category_id,
    category_name
FROM
    SubCategories
order by category_id;





    
    
    
-- 재고 얻기
select p.product_id, s.quantity
	from product p
    inner join stock s
    on p.product_id = s.product_id;
    
    
-- 상품의 상세 이미지 찾기
select pdi.path '이미지 경로', pdi.kind_of '종류', pdi.order_num '순서'
	from product p
    inner join product_description_img pdi
    on p.product_description_id = pdi.product_description_id
    where pdi.product_description_id = 'GUCCI00000002' and pdi.is_used='Y'
    order by pdi.kind_of, pdi.order_num ASC;
    
-- 상품 삭제시 재고도 같이 삭제.


-- 랭킹 조회수순.
select *
	from product
    order by view_count Desc;
-- 랭킹 별점 순.
select *
	from product
    order by star_rating Desc;
    
-- 랭킹 판매 순
	select *
	from product
    order by sales_quantity Desc;
    
/*
	카테고리 랭킹 별점 순	
	카테고리는 BFS그래프로 나타내서 구하기
	예시 C03 이면 여성 의류
	여성의류 카테고리를 클릭하면 C03, C08, C09, C10 에 해당하는 의류 상품이 나와야함.
    
*/


select * 
	from product
    where category_id IN('C03','C08','09','C08')
    order by sales_quantity Desc;



WITH paginated_products AS (
    SELECT 
        p.product_id AS productId,
        p.name AS productName,
        p.price AS price,
        p.rep_img AS repImg,
        p.star_rating AS starRating,
        b.brand_id AS brandId,
        b.name AS brandName
    FROM product p
    INNER JOIN brand b
    ON p.brand_id = b.brand_id
    WHERE p.brand_id = 'A00000000002'
    ORDER BY p.product_id
    LIMIT 2 OFFSET 0
),
total_count AS (
    SELECT COUNT(*) AS totalCount
    FROM product p
    WHERE p.brand_id = 'A00000000002'
)
SELECT
    paginated_products.*,
    total_count.totalCount
FROM paginated_products
CROSS JOIN total_count;


select count(*)
from product p
INNER JOIN brand b
ON p.brand_id = b.brand_id
 where p.brand_id = 'A00000000002';
-- group by b.bradn_id ='A00000000002';