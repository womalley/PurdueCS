set serveroutput on size 32000

-- Question 1: Retailer detail
create or replace procedure RetailerDetail (id IN Retailers.RetailerId%TYPE) as
	
	/* declarations */
	retName varchar(255);
	retID number;
	retAddr varchar(255);
	retOrder number;
	retPopular varchar(255);
	retSold number;
	popularName varchar(255);

	CURSOR c1 IS SELECT * FROM Retailers WHERE Retailers.RetailerID = id;

BEGIN
	OPEN c1;
	FETCH c1 INTO retID, retName, retAddr;
	DBMS_OUTPUT.PUT('Retailer Name: ' || retName);
	DBMS_OUTPUT.NEW_LINE();
	DBMS_OUTPUT.PUT('Retailer Address: ' || retAddr);
	DBMS_OUTPUT.NEW_LINE();

	SELECT COUNT(RetailerID) INTO retOrder FROM Orders WHERE RetailerID = id;
        DBMS_OUTPUT.PUT('Retailer Total Orders: ' || retOrder);
	DBMS_OUTPUT.NEW_LINE();

	SELECT Products.ProductName, SUM(Orders.Count) AS total INTO popularName, retPopular FROM Orders JOIN Products ON Orders.ProductID=Products.ProductID WHERE Orders.RetailerID = id GROUP BY Products.ProductName, Orders.ProductID ORDER BY total DESC FETCH FIRST ROW ONLY;
	DBMS_OUTPUT.PUT('Most Popular Product: ' || popularName);
	DBMS_OUTPUT.NEW_LINE();

	SELECT SUM(Orders.Count) AS total INTO retSold FROM Orders WHERE Orders.RetailerID = id GROUP BY Orders.ProductID ORDER BY total DESC FETCH FIRST ROW ONLY;
	DBMS_OUTPUT.PUT('Total Sold: ' || retSold);
	DBMS_OUTPUT.NEW_LINE();

	CLOSE c1;
	

END RetailerDetail;
/

-- make test cases yourselves
BEGIN
RetailerDetail(1);
end;
/

-- Queation 2: Monthly delay report
create or replace procedure MonthlyDelayReport as

	CURSOR c IS SELECT RetailerName, OrderDate, to_char(OrderDate, 'YYYY') as y, to_char(OrderDate, 'fmMM') as m FROM Orders JOIN Retailers ON Orders.RetailerID = Retailers.RetailerID WHERE Status = 'DELAYED' ORDER BY y, m, RetailerName ASC;

	counter NUMBER;
	monthNum NUMBER;
	rYear NUMBER;
	rMonth NUMBER;
	mCount NUMBER;

BEGIN
	rYear := '2000';
	rMonth := '0';

	FOR counter IN 1 .. 12
	LOOP
		FOR tmp IN c
		LOOP
			IF((tmp.y != rYear OR tmp.m != rMonth) AND tmp.m = to_char(counter)) THEN
				SELECT COUNT(RetailerID) INTO mCount FROM Orders WHERE to_char(OrderDate, 'YYYY') = tmp.y AND to_char(OrderDate, 'fmMM') = tmp.m AND Status = 'DELAYED';
				
				DBMS_OUTPUT.PUT('Delayed orders in ' || tmp.y || '-' || tmp.m || ': ' || mCount);
				DBMS_OUTPUT.NEW_LINE();
				DBMS_OUTPUT.PUT('Retailers with delayed orders:');
				DBMS_OUTPUT.NEW_LINE();

				FOR rName IN (SELECT DISTINCT RetailerName FROM Orders JOIN Retailers ON Orders.RetailerID = Retailers.RetailerID WHERE to_char(OrderDate, 'YYYY') = tmp.y AND to_char(OrderDate, 'fmMM') = tmp.m AND Orders.Status = 'DELAYED' ORDER BY REtailerName ASC)
				LOOP
					DBMS_OUTPUT.PUT_LINE(rName.RetailerName);
				END LOOP;
		 END IF;
			rYear := tmp.y;
			rMonth := tmp.m;
		END LOOP;
	END LOOP;
END MonthlyDelayReport;
/
BEGIN
	MonthlyDelayReport;
End;
/


-- Question 3: Find the product with least profit in each category
create or replace procedure LeastProfitProduct as

	avgUnitPrice NUMBER;
	CURSOR c3 IS SELECT DISTINCT Category FROM Product;

BEGIN
	
	FOR catAttr IN c3
	LOOP
		avgUnitPrice := 1000000000;
		DBMS_OUTPUT.PUT('Least Profit in ' || catAttr.Category);
		DBMS_OUTPUT.NEW_LINE();
	
		FOR tmp IN (SELECT AVG(Orders.UnitPrice - Products.ExfactoryPrice) AS profit, ProductName FROM Products JOIN Orders ON Orders.ProductId = Products.ProductId WHERE Category = catAttr.Category GROUP BY ProductName)
		LOOP
			IF(tmp.profit <= avgUnitPrice) THEN
				avgUnitPrice := tmp.profit;
			END IF;
		END LOOP;

		FOR tmp1 IN (SELECT AVG(Orders.UnitPrice - Products.ExfactoryPrice) AS profit, ProductName FROM Products JOIN Orders ON Orders.ProductId = Products.ProductId WHERE Category = catAttr.Category GROUP BY ProductName)
		LOOP
			IF (tmp1.profit = avgUnitPrice) THEN
				DBMS_OUTPUT.PUT('- ' || tmp1.ProductName || ': ' || tmp1.profit); 
				DBMS_OUTPUT.NEW_LINE();
			END IF;
		END LOOP;
	END LOOP;
	

END LeastProfitProduct;
/

BEGIN
	LeastProfitProduct;
END;
/


-- Queation 4: New table for retailer product category distribution
create table RetailerCatergoryTable(RetailerId integer, Electronic integer, Apparel integer, Books integer, primary key(RetailerId));
create or replace procedure RetailerProductCatergory as

	electronics NUMBER;
	apparel NUMBER;
	books NUMBER;

	CURSOR c4 IS SELECT DISTINCT RetailerId FROM Retailers;


BEGIN
	FOR tmp IN c4 
	LOOP
		SELECT COUNT(OrderId) INTO electronics FROM Products JOIN Orders ON Products.ProductId = Orders.ProductId WHERE Category = 'electronics' AND Orders.RetailerId = tmp.RetailerId;
                SELECT COUNT(OrderId) INTO apparel FROM Products JOIN Orders ON Products.ProductId = Orders.ProductId WHERE Category = 'apparel' AND Orders.RetailerId = tmp.RetailerId;	
                SELECT COUNT(OrderId) INTO books FROM Products JOIN Orders ON Products.ProductId = Orders.ProductId WHERE Category = 'books' AND Orders.RetailerId = tmp.RetailerId;

		INSERT INTO RetailerCatergoryTable
		(RetailerId, Electronic, Apparel, Books)
		VALUES
		(tmp.RetailerId, electronics, apparel, books);

	END LOOP;

END RetailerProductCatergory;
/

BEGIN
RetailerProductCatergory;
END;
/
select * from RetailerCatergoryTable;

drop table RetailerCatergoryTable;


-- Question 5: Exception Handle
create or replace procedure CustomerProductInfo(cid IN Customers.CustomerId%TYPE, pid IN Products.ProductId%TYPE) as

	oDate DATE;
BEGIN

	SELECT OrderDate INTO oDate FROM Orders WHERE Orders.ProductId = pid AND Orders.CustomerId = cid;
	DBMS_OUTPUT.PUT('Records of customer id ' || cid || ' with product id ' || pid || ':');
	DBMS_OUTPUT.NEW_LINE();
	DBMS_OUTPUT.PUT('OrderDate: ' || oDate);
	DBMS_OUTPUT.NEW_LINE();
	DBMS_OUTPUT.NEW_LINE();

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		DBMS_OUTPUT.PUT('Records of customer id ' || cid || ' with product id ' || pid || ':');
		DBMS_OUTPUT.NEW_LINE();
		DBMS_OUTPUT.PUT('Invalid customer id or product id!');
		DBMS_OUTPUT.NEW_LINE();
		DBMS_OUTPUT.NEW_LINE();	

END CustomerProductInfo;
/

BEGIN
CustomerProductInfo(1,1);
END;
/

BEGIN
CustomerProductInfo(-1,1);
END;
/
