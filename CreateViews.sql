-- Sahil Patel and Kevin Mody
use Pizzeria;

/*
CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition; 
*/

/*
create view ToppingPopularity as
select ToppinngName as Topping, 
sum
*/

create view ToppingPopularity as
select BaseToppingToppingID, sum(BaseToppingToppingID) as ToppingCount from toppingCurrent inner join baseTopping on toppingCurrent.ToppingCurrentBaseToppingID = baseTopping.BaseToppingID
group by BaseToppingToppingID
order by sum(BaseToppingToppingID);

create view ProfitByOrderType as
select CustomerOrderType as Customertype, CustomerOrderTimeStamp as OrderDate, CustomerOrderTotalPrice as TotalOrderPrice, CustomerOrderTotalCost as TotalOrderCost,
(CustomerOrderTotalPrice - CustomerOrderTotalCost) as Profit from customerOrder;

