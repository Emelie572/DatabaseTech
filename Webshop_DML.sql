select * from ProductType;
select * from ProductCategory;
select * from Brand;
select * from Product;
select * from BelongTo;
select * from ProductOption;
select * from Customer;
select * from CustomerOrder;
select * from OrderItem;


select Customer.first_name as Firstname, Customer.last_name as Lastname, ProductCategory.product_category_name as Category,Brand.brand_name as Brand, ProductOption.Size, ProductOption.Color
from CustomerOrder
inner join OrderItem on CustomerOrder.order_id = OrderItem.order_id
inner join ProductOption on OrderItem.product_option_id = ProductOption.product_option_id
inner join Product on ProductOption.product_id = Product.product_id
inner join BelongTo on Product.product_id = BelongTo.product_id
inner join ProductCategory on BelongTo.product_category_id = ProductCategory.product_category_id
inner join Brand on Product.brand_id = Brand.brand_id
inner join Customer on CustomerOrder.customer_id = Customer.customer_id
where Brand.brand_name = 'Adidas' 
and ProductCategory.product_category_name = 'Sneakers'
and ProductOption.size = 38 
and ProductOption.color = 'Svart';

select ProductCategory.product_category_name as Category, count(Product.product_id) as TotalProducts
from Product
left join BelongTo on Product.product_id = BelongTo.product_id
left join ProductCategory on BelongTo.product_category_id = ProductCategory.product_category_id
group by ProductCategory.product_category_name;

select Customer.first_name as Firstname, Customer.last_name as Lastname, sum(CustomerOrder.total_price) as Totalprice
from CustomerOrder
left join Customer on CustomerOrder.customer_id = Customer.customer_id
group by Customer.first_name, Customer.last_name;

select Customer.muniplicity as Muniplicity, sum(CustomerOrder.total_price) as OrderValue
from CustomerOrder
inner join Customer on CustomerOrder.customer_id = Customer.customer_id
group by Customer.muniplicity
having sum(CustomerOrder.total_price) > 1000;

select Product.product_name as Top5BestSellingProducts, sum(OrderItem.order_item_quantity) as ItemsSold
from CustomerOrder 
inner join OrderItem on CustomerOrder.order_id = OrderItem.order_id
inner join ProductOption on OrderItem.product_option_id = ProductOption.product_option_id
inner join Product on ProductOption.product_id = Product.product_id
group by Product.product_name
order by ItemsSold desc
limit 5;

select date_format(CustomerOrder.order_date,'%y/%m') as HighestSalesYearAndMonth, sum(CustomerOrder.total_price) as TotalSales
from CustomerOrder
inner join Customer on CustomerOrder.customer_id = Customer.customer_id
group by date_format(CustomerOrder.order_date,'%y/%m')
order by TotalSales desc
limit 1;
