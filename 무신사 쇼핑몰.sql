CREATE TABLE if not exists `member_info` (
  `member_number` INT NOT NULL AUTO_INCREMENT,
  `member_state_code` VARCHAR(50) NOT NULL,
  `id` VARCHAR(50) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL,
  `sex` VARCHAR(1) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `login_datetime` DATETIME NULL,
  `modify_datetime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `register_datetime` DATETIME NOT NULL,
  `exit_datetime` DATETIME NOT NULL DEFAULT '9999-12-31 23:59:59',
  `recommand_id` VARCHAR(50) NULL,
  `login_count` TINYINT NOT NULL DEFAULT 0,
  `is_admin` VARCHAR(1) NOT NULL DEFAULT 'U',
  `note` TEXT NULL,
  PRIMARY KEY (`member_number`)
);


CREATE TABLE if not exists `admin_brand` (
  `id` VARCHAR(50) NOT NULL,
  `brand_code` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));



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
    brand_product_code VARCHAR(25),
    category_id VARCHAR(3) not null,
    brand_id VARCHAR(25) not null,
    name VARCHAR(50) NOT NULL,
    rep_img VARCHAR(500) NOT NULL,
    price INT NOT NULL,
    event_price INT default 0 NOT NULL,
    product_option_depth tinyint default 1 not null,
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

create table if not exists product_option (
	product_id VARCHAR(25),
	product_option_id VARCHAR(10),
    option_category VARCHAR(10),
    option_name VARCHAR(10),
    option_depth VARCHAR(10),
    constraint option_tb primary key(product_id, product_option_id),
    CONSTRAINT product_option_product_id_fk
		FOREIGN KEY (product_id)
		REFERENCES product(product_id)
);


create table if not exists stock(
    product_id VARCHAR(25),
    product_option_id VARCHAR(10),
    quantity int default 0 not null,
    create_datetime datetime default now(),
    sale_state VARCHAR(5) not null default 'SELL',
    constraint stock_tb primary key(product_id, product_option_id),
    CONSTRAINT stock_product_id_fk
		FOREIGN KEY (product_id, product_option_id)
        REFERENCES product_option(product_id, product_option_id)
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
-- 회원이 삭제 되어도 리뷰는 삭제되지 않도록, fk인 member_id는 null로 
create table if not exists review(
	review_id int primary KEY  auto_increment,
    product_id VARCHAR(20) NOT NULL,
    member_number int,
    content VARCHAR(500) NOT NULL,
    review_img VARCHAR(500),
    star TINYINT NOT NULL,
    like_count INT default 0,
    create_datetime DATETIME default NOW(),
    modify_datetime DATETIME default NOW(),
    CONSTRAINT review_member_fk
		foreign key (member_number)
        references member_info(member_number)
        ON DELETE SET NULL,
	CONSTRAINT review_product_fk
		foreign key (product_id)
        references product(product_id)
);
