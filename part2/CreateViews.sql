-- Sahil Patel and Kevin Mody
use Pizzeria;

/*
CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition; 
*/

drop view if exists ToppingPopularity;
create view ToppingPopularity as
select t.ToppingName as Topping,  sum(ToppingCurrentCounter) as ToppingCount
from toppingCurrent as tc
inner join baseTopping as bt on tc.ToppingCurrentBaseToppingID = bt.BaseToppingID
inner join topping as t on t.ToppingID = bt.BaseToppingToppingID
group by t.ToppingID
order by sum(ToppingCurrentCounter) desc;

drop view if exists ProfitByPizza;
create view ProfitByPizza as
select
s.SizeType as "Pizza Size", 
c.CrustType as "Pizza Crust", 
sum(PizzaTotalPrice - PizzaTotalCost) as Profit, 
date_format(max(co.CustomerOrderTimeStamp), "%M-%d-%Y") as LastOrderDate
from pizza as p
inner join customerOrder as co on p.PizzaOrderID = co.CustomerOrderID
inner join size as s on p.PizzaSizeID = s.SizeID
inner join crust as c on p.PizzaCrustID = c.CrustID
group by p.PizzaSizeID,  p.PizzaCrustID
order by Profit desc;

drop view if exists ProfitByOrderType;
create view ProfitByOrderType as
select
CustomerOrderType as CustomerType,
date_format(CustomerOrderTimeStamp, "%Y-%M") as OrderDate,
CustomerOrderTotalPrice as TotalOrderPrice,
CustomerOrderTotalCost as TotalOrderCost,
(CustomerOrderTotalPrice - CustomerOrderTotalCost) as Profit
from customerOrder
group by CustomerOrderType,  date_format(CustomerOrderTimeStamp, "%Y%m");

insert into ProfitByOrderType values
("", "GrandTotal", 
cast((SELECT sum(TotalOrderPrice) FROM Pizzeria.ProfitByOrderType) as decimal(8,2)),
cast((SELECT sum(TotalOrderCost) FROM Pizzeria.ProfitByOrderType) as decimal(8,2)),
cast((SELECT sum(Profit) FROM Pizzeria.ProfitByOrderType) as decimal(8,2)));

