drop database if exists Webshop;
create database Webshop;
use Webshop;

create table ProductType(
    product_type_id int not null auto_increment primary key,
    product_type_name varchar(100) not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table ProductCategory(
    product_category_id int not null auto_increment primary key,
    product_category_name varchar(100) not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    product_type_id int not null,
    foreign key (product_type_id) references ProductType(product_type_id)
);

create table Brand(
    brand_id int not null auto_increment primary key,
    brand_name varchar(100) not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table Product(
    product_id int not null auto_increment primary key,
    product_name varchar(100) not null,
    product_price double not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    brand_id int not null,
    foreign key (brand_id) references Brand (brand_id) 
);

create table BelongTo(
    product_id int not null,
    product_category_id int not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    primary key (product_id, product_category_id),
    foreign key (product_id) references Product(product_id) on delete cascade, -- Om produkten tas bort har vi inte längre användning för kopplingen, då produkten inte existerar
    foreign key (product_category_id) references ProductCategory(product_category_id) on delete cascade -- Om produktkategorin tas bort har vi inte längre användning för kopplingen, då kategorin inte existerar
);


create table ProductOption(
    product_option_id int not null auto_increment primary key,
    size int not null,
    color varchar(100) not null,
    stock int not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    product_id int not null,
    foreign key (product_id) references Product (product_id) 
);

create table Customer(
    customer_id int not null auto_increment primary key,
    personal_number char(10) not null unique ,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    user_name varchar(50) not null,
    user_password varchar(50) not null,
    muniplicity varchar(50) not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table CustomerOrder(
    order_id int not null auto_increment primary key,
    order_date date not null,
    order_status ENUM('AKTIV', 'BETALD') NOT NULL,
    total_price double not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    customer_id int not null,
    foreign key (customer_id) references Customer (customer_id) 
);


create table OrderItem(
    order_item_id int not null auto_increment primary key,
    order_item_quantity int not null,
    created timestamp default CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    order_id int not null,
    product_option_id int not null,
    foreign key (order_id) references CustomerOrder (order_id) on delete cascade, -- Utan Order så har vi ingen användning för orderitem, om en order raderas så blir det massa hängande orderrader som inte är kopplade till nåt vilket ger onödig data i databasen
    foreign key (product_option_id) references ProductOption (product_option_id) on delete cascade -- om ett produktval tas bort så är det bra att ta bort orelaterade orderrader då de inte längre refererar till något
);


create index IX_product_name on Product(product_name); -- Då produktnamn används vid direkta sökningar av specifika produkter
create index IX_brand_name on Brand(brand_name); -- Eftersom användare ofta söker och filtrerar produkter på baserat på varumärke


insert into ProductType(product_type_name)values
('Skor');

insert into ProductCategory(product_category_name,product_type_id)values
('Sneakers',1),
('Loafers',1),
('Boots',1),
('Ballerinaskor',1),
('Pumps',1),
('Vandringsskor',1),
('Män',1),
('Kvinnor',1),
('Barn',1);

insert into Brand(brand_name)values
('Adidas'),
('Puma'),
('Novita'),
('Saloman'),
('Wildflower'),
('STENK');

insert into Product(product_name,product_price,brand_id)values
('Adidas Sneakers',899,1),
('Puma Sneakers',999,2),
('Novita Manarola Ballerina',1400,3),
('Novita Noli Loafers',2100,3),
('Salomon Outblast TS Kängor',1800,4),
('Wildflower Dalston',450,5),
('Novita Novara Gold Collection',1800,3),
('STENK Vidfors Kängor Vibram',1300,6);

insert into BelongTo(product_category_id,product_id)values
(1,1),
(1,2),
(2,4),
(3,5),
(3,8),
(4,3),
(4,6),
(5,7),
(6,5),
(6,8),
(7,5),
(8,1),
(8,2),
(8,3),
(8,4),
(8,7),
(8,8),
(9,6);

insert into ProductOption(size,color,stock,product_id)values
(36,'Svart',100,1),
(37,'Svart',59,1),
(38,'Svart',90,1),
(39,'Svart',85,1),
(36,'Vit',130,2),
(37,'Vit',112,2),
(38,'Vit',50,2),
(39,'Vit',80,2),
(36,'Vit',111,3),
(37,'Vit',10,3),
(38,'Vit',80,3),
(39,'Vit',43,3),
(36,'Svart',111,3),
(37,'Svart',10,3),
(38,'Svart',80,3),
(39,'Svart',43,3),
(36,'Lila',101,4),
(37,'Lila',76,4),
(38,'Lila',94,4),
(39,'Lila',54,4),
(42,'Grå',101,5),
(43,'Grå',76,5),
(44,'Grå',94,5),
(45,'Grå',54,5),
(31,'Rosa',33,6),
(32,'Rosa',56,6),
(33,'Rosa',69,6),
(34,'Rosa',13,6),
(36,'Vit',190,7),
(37,'Vit',102,7),
(38,'Vit',85,7),
(39,'Vit',32,7),
(36,'Brun',130,8),
(37,'Brun',122,8),
(38,'Brun',85,8),
(39,'Brun',31,8);

insert into Customer(personal_number, first_name, last_name, muniplicity) values
('0000000000', 'John', 'Doe', 'Test City'),
('0000000001', 'Jane', 'Smith', 'Sample Town'),
('0000000002', 'Alice', 'Johnson', 'Example City'),
('0000000003', 'Bob', 'Brown', 'Demo Town'),
('0000000004', 'Charlie', 'Davis', 'Test Village');

insert into CustomerOrder(order_date,order_status,total_price,customer_id)values
('2024-05-11','BETALD',1349,1),
('2024-06-13','BETALD',1798,2),
('2024-06-14','BETALD',1400,3),
('2024-07-18','BETALD',999,3),
('2024-08-01','BETALD',450,4),
('2024-11-19','BETALD',450,5),
('2025-01-01','BETALD',1300,5);

insert into OrderItem(order_item_quantity,product_option_id,order_id)values
(1,3,1),
(1,25,1),
(2,3,2),
(1,12,3),
(1,7,5),
(1,29,4),
(1,27,6),
(1,33,7);

