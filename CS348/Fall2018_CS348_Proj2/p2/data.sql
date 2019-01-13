--Customer
insert into Customers(CustomerId, FirstName, LastName, Address) values (1, 'Cust A', 'Cust Last a', 'ddress1');
insert into Customers(CustomerId, FirstName, LastName, Address) values (2, 'Cust B', 'Cust Last b', 'ddress2');
insert into Customers(CustomerId, FirstName, LastName, Address) values (3, 'Cust C', 'Cust Last c', 'ddress3');
insert into Customers(CustomerId, FirstName, LastName, Address) values (4, 'Cust D', 'Cust Last d', 'ddress4');
insert into Customers(CustomerId, FirstName, LastName, Address) values (5, 'Cust E', 'Cust Last e', 'ddress5');
insert into Customers(CustomerId, FirstName, LastName, Address) values (6, 'Cust F', 'Cust Last f', 'ddress6');
insert into Customers(CustomerId, FirstName, LastName, Address) values (7, 'Cust G', 'Cust Last g', 'ddress6');
insert into Customers(CustomerId, FirstName, LastName, Address) values (8, 'Cust H', 'Cust Last h', 'ddress7');
insert into Customers(CustomerId, FirstName, LastName, Address) values (9, 'Cust I', 'Cust Last i', 'ddress6');
insert into Customers(CustomerId, FirstName, LastName, Address) values (10, 'Cust J', 'Cust Last j', 'ddress10');

--Company
insert into Companies(CompanyId, CompanyName, Address, State) values (1, 'Company A', 's_ddress1', 'CA');
insert into Companies(CompanyId, CompanyName, Address, State) values (2, 'Company B', 's_ddress2', 'IN');
insert into Companies(CompanyId, CompanyName, Address, State) values (3, 'Company C', 's_ddress3', 'IL');
insert into Companies(CompanyId, CompanyName, Address, State) values (4, 'Company D', 's_ddress4', 'NA');
insert into Companies(CompanyId, CompanyName, Address, State) values (5, 'Company E', 's_ddress5', 'IN');
insert into Companies(CompanyId, CompanyName, Address, State) values (6, 'Company F', 's_ddress6', 'AZ');
insert into Companies(CompanyId, CompanyName, Address, State) values (7, 'Company G', 's_ddress7', 'CA');

--Retailer
insert into Retailers(RetailerId, RetailerName, Address) values (1, 'Retailer A', 'r_ddress1');
insert into Retailers(RetailerId, RetailerName, Address) values (2, 'Retailer B', 'r_ddress2');
insert into Retailers(RetailerId, RetailerName, Address) values (3, 'Retailer C', 'r_ddress3');
insert into Retailers(RetailerId, RetailerName, Address) values (4, 'Retailer D', 'r_ddress4');
insert into Retailers(RetailerId, RetailerName, Address) values (5, 'Retailer E', 'r_ddress5');


--Product
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (1, 'Iphone', 'electronics', 1, 500.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (2, 'OnePlus 5t', 'electronics', 2, 320.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (3, 'Nike sports jacket', 'apparel', 3, 8.5);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (4, 'Adidas cap', 'apparel', 4, 4.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (5, 'inferno', 'text_books', 5, 30.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (6, 'paths of glory', 'books', 5, 39.9);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (7, 'LG 72 inch LED TV', 'electronics', 6, 200.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (8, 'da vinci code', 'books', 5, 20.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (9, 'Macbook', 'electronics', 1, 600.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (10, 'Foundation of Crypto', 'books', 5, 30.0);
insert into Products(ProductId, ProductName, Category, CompanyId, ExfactoryPrice) values (11, 'Pixel Book', 'electronics', 7, 500.0);

--Inventory
insert into RetailerInventories(ProductId, RetailerId, TotalStock) values (1,1,10);
insert into RetailerInventories(ProductId, RetailerId, TotalStock) values (10,1,100);
insert into RetailerInventories(ProductId, RetailerId, TotalStock) values (5,1,10);
insert into RetailerInventories(ProductId, RetailerId, TotalStock) values (11,2,8);
insert into RetailerInventories(ProductId, RetailerId, TotalStock) values (9,2,5);


--Orders
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (1,1,1,1,15,600, to_date('20180101','YYYYMMDD'),'DELAYED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (2,2,1,10,45,60, to_date('20180102','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (3,2,1,5,45,60, to_date('20180103','YYYYMMDD'),'DELAYED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (4,3,2,7,1,300, to_date('20180104','YYYYMMDD'),'SHIPPED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (5,4,3,3,3,20, to_date('20180201','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (6,5,1,4,2,15, to_date('20180301','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (7,2,1,5,45,60, to_date('20180202','YYYYMMDD'),'DELAYED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (8,6,4,2,1,400, to_date('20180401','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (9,7,5,11,1,600, to_date('20180503','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (10,8,2,11,10,550, to_date('20180601','YYYYMMDD'),'DELAYED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (11,9,2,9,2,800, to_date('20180701','YYYYMMDD'),'DELIVERED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (12,10,1,8,1,50, to_date('20180301','YYYYMMDD'),'SHIPPED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (13,8,2,9,10,660, to_date('20180201','YYYYMMDD'),'DELAYED');
insert into Orders(OrderId, CustomerId, RetailerId, ProductId, Count, UnitPrice, OrderDate, Status) values (14,9,1,5,25,60, to_date('20180501','YYYYMMDD'),'DELAYED');

