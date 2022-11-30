-- Sahil Patel and Kevin Mody

use Pizzeria;
select * from toppingCurrent;
select * from pizza;
select * from dineIn;
select * from delivery;
select * from pickup;
select * from customerOrder;
select * from discount; 
select * from customer;
select * from baseTopping;
select * from topping;
select * from baseCost;
select * from size;
select * from crust;
select * from pizzaDiscount;
select * from orderDiscount;

select * from ProfitByOrderType;
select * from ProfitByPizza;
select * from ToppingPopularity;


/*
-- PizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter 
select 
PizzaID, ToppingCurrentBaseToppingID, ToppingName, SizeType, ToppingCurrentCounter, PizzaTotalCost, PizzaTotalPrice, ToppingPrice, ToppingCost, BaseToppingUnit,
(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as CurrentToppingCost_calc
from toppingCurrent
inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
inner join pizza on PizzaID = ToppingCurrentPizzaID
inner join topping on ToppingID = BaseToppingToppingID
inner join size on SizeID = PizzaSizeID
where PizzaID in (11, 12, 15, 16);
-- where PizzaID in (11, 12, 13, 14, 15, 16);

select bt.BaseToppingID, size.SizeType, topping.ToppingName, bt.BaseToppingUnit
from baseTopping as bt
inner join size on bt.BaseToppingSizeID = size.SizeID
inner join topping on bt.BaseToppingToppingID = topping.ToppingID
order by bt.BaseToppingID;

select size.SizeID, size.SizeType, crust.CrustID, crust.CrustType
from baseCost as bc
inner join size on bc.BaseCostSizeID = size.SizeID
inner join crust on bc.BaseCostCrustID = crust.CrustID
order by size.SizeID;

-- pd.PizzaDiscountID, p.PizzaID, pd.PizzaDiscountDiscountID
select * from pizzaDiscount as pd
inner join discount as d on d.DiscountID = pd.PizzaDiscountDiscountID
inner join pizza as p on p.PizzaID = pd.PizzaDiscountPizzaID
inner join customerOrder as o on o.CustomerOrderID = p.PizzaOrderID;

*/




/*

select * from ;
select * from ;
select * from ;
select * from ;
select * from ;
*/