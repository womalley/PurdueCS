-- Query1
SELECT category, COUNT(category) as NumberOfProducts FROM product GROUP BY category ORDER BY NumberOfProducts DESC;

-- Query2
SELECT category, TO_CHAR(AVG(ExFactoryPrice), '99999999999.99') as AveragePrice FROM product GROUP BY category ORDER BY AveragePrice DESC;

-- Query3
SELECT productid, productname FROM product prod WHERE prod.companyid IN (SELECT companyid FROM company comp WHERE comp.state = 'IN' OR comp.state = 'CA') ORDER BY productid ASC;

-- Query4
SELECT orders.retailerid, orders.productid, ((retailerinventory.totalstock-SUM(orders.count))*(-1)) AS PrepareNumber FROM orders JOIN retailerinventory ON status='PENDDING' AND orders.productid=retailerinventory.productid GROUP BY orders.productid, orders.retailerid, retailerinventory.totalstock HAVING (-1 < SUM(orders.count)-retailerinventory.totalstock) ORDER BY orders.retailerid; 

-- Query5
SELECT productname, SUM(orders.count) AS NumberOfITemsSold FROM orders JOIN product ON product.productid=orders.productid GROUP BY productname ORDER BY NumberOfItemsSold DESC FETCH FIRST 3 ROWS ONLY;

-- Query6
SELECT customer.firstname, customer.lastname, TO_CHAR(SUM(unitprice*orders.count), '99999999999.99') AS TotalAmount FROM orders JOIN customer ON customer.customerid = orders.customerid GROUP BY orders.customerid, customer.firstname, customer.lastname ORDER BY TotalAmount DESC FETCH FIRST 3 ROWS ONLY;

-- Query7
SELECT retailer.retailerid, retailer.retailername, (COUNT(orderid)) AS NumberOfOrders FROM orders JOIN retailer ON retailer.retailerid = orders.retailerid GROUP BY retailer.retailerid, retailer.retailername ORDER BY NumberOfOrders DESC FETCH first 2 ROWS ONLY;


-- Query8
SELECT retailer.retailerid, retailer.retailername, TO_CHAR(SUM((unitprice-exfactoryprice)*orders.count), '99999999999.99') AS NetProfit FROM orders JOIN product ON orders.productid=product.productid JOIN retailer ON orders.retailerid=retailer.retailerid GROUP BY retailer.retailerid, retailer.retailername ORDER BY NetProfit DESC FETCH FIRST 2 ROWS ONLY;


-- Query9
SELECT company.companyid, company.companyname, SUM(orders.count) AS TotalCount FROM company JOIN product ON company.companyid=product.companyid JOIN orders ON product.productid=orders.productid GROUP BY company.companyid, company.companyname ORDER BY TotalCount DESC FETCH FIRST 3 ROWS ONLY;


-- Query10
SELECT retailer.retailerid, retailer.retailername, COUNT(DISTINCT orders.customerid) AS NumberOfDistinctCustomers FROM retailer JOIN orders ON retailer.retailerid=orders.retailerid JOIN customer ON customer.customerid=orders.customerid GROUP BY retailer.retailerid, retailer.retailername ORDER BY NumberOfDistinctCustomers DESC FETCH FIRST 2 ROWS ONLY;

