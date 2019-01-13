create table Customer(CustomerId integer, FirstName varchar(30), LastName varchar(30), Address varchar(20), primary key(CustomerId));
create table Company(CompanyId integer, CompanyName varchar(30), Address varchar(20), State varchar(2), primary key(CompanyId));
create table Retailer(RetailerId integer, RetailerName varchar(30), Address varchar(20), primary key(RetailerId));
create table Product(ProductId integer, ProductName varchar(30), Category varchar(30), CompanyId integer, ExFactoryPrice Number(10,4), primary key(ProductId));
create table Orders(OrderId integer, CustomerId integer, RetailerId integer, ProductId integer, Count integer, UnitPrice Number(10,4), OrderDate date, Status varchar(10),  primary key(OrderId));
create table RetailerInventory(ProductId integer, RetailerId integer, TotalStock integer);
