-- Sahil Patel and Kevin Mody
use Pizzeria;
/*
delete from toppingCurrent where ToppingCurrentPizzaID = 1;
delete from pizza where PizzaID = 1;
delete from customerOrder where CustomerOrderID = 2;
*/

select bt.BaseToppingID, size.SizeType, topping.ToppingName, bt.BaseToppingUnit
from baseTopping as bt
inner join size on bt.BaseToppingSizeID = size.SizeID
inner join topping on bt.BaseToppingToppingID = topping.ToppingID
order by bt.BaseToppingID;


select bt.BaseToppingID, size.SizeType, size.SizeID, topping.ToppingName, bt.BaseToppingUnit
from baseTopping as bt
inner join size on bt.BaseToppingSizeID = size.SizeID 
inner join topping on bt.BaseToppingToppingID = topping.ToppingID
where size.SizeID = 4
order by bt.BaseToppingID;


select * from baseTopping
where BaseToppingID in 
	(select ToppingCurrentBaseToppingID from toppingCurrent
    where ToppingCurrentPizzaID = 1);


-- Get current topping with topping info, BaseToppingUnit corresponding to pizza size
select * from topping as t
inner join
	(select * from baseTopping
	where BaseToppingID in 
		(select ToppingCurrentBaseToppingID from toppingCurrent
		where ToppingCurrentPizzaID = 1)) as bt on bt.BaseToppingToppingID = t.ToppingID
inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID;
        

-- Current Pizza's topping's total price. Just change the ToppingCurrentPizzaID = <int value>
select sum(ToppingPrice * ToppingCurrentCounter) from topping as t
inner join
	(select * from baseTopping
	where BaseToppingID in 
		(select ToppingCurrentBaseToppingID from toppingCurrent
		where ToppingCurrentPizzaID = 1)) as bt on bt.BaseToppingToppingID = t.ToppingID
inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID;

-- Current Pizza's topping's total cost. Just change the ToppingCurrentPizzaID = <int value>
select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) from topping as t
inner join
	(select * from baseTopping
	where BaseToppingID in 
		(select ToppingCurrentBaseToppingID from toppingCurrent
		where ToppingCurrentPizzaID = 1)) as bt on bt.BaseToppingToppingID = t.ToppingID
inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID;

-- Get current Pizza's base price
select BaseCostPrize from baseCost where BaseCostSizeID = 3 AND BaseCostCrustID = 1;

-- Get current Pizza's base cost
select BaseCostCost from baseCost where BaseCostSizeID = 3 AND BaseCostCrustID = 1;


-- Get all Pizza corresponding to customerOrderID = <int>
select * from customerOrder
inner join pizza on pizza.PizzaOrderID = customerOrder.CustomerOrderID
where CustomerOrderID = 1;

select * from pizza
where PizzaOrderID = 1;



select
((cast((select sum(ToppingPrice * ToppingCurrentCounter) from topping as t
inner join
	(select * from baseTopping
	where BaseToppingID in 
		(select ToppingCurrentBaseToppingID from toppingCurrent
		where ToppingCurrentPizzaID = 1)) as bt on bt.BaseToppingToppingID = t.ToppingID
inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID) as double) + 
cast((select BaseCostPrize from baseCost where BaseCostSizeID = 3 AND BaseCostCrustID = 1) as double)));

delimiter !
create function getPizzaTotalPrice (sID int, cID int, pID int) returns double
begin
	return
    (cast((select sum(ToppingPrice * ToppingCurrentCounter) from topping as t
	inner join
		(select * from baseTopping
		where BaseToppingID in 
			(select ToppingCurrentBaseToppingID from toppingCurrent
			where ToppingCurrentPizzaID = pID)) as bt on bt.BaseToppingToppingID = t.ToppingID
	inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID) as double) + 
	cast((select BaseCostPrize from baseCost where BaseCostSizeID = sID AND BaseCostCrustID = cID) as double));
end !
delimiter ;

delimiter !
create function getPizzaTotalCost (sID int, cID int, pID int) returns double
begin
	return
    cast((select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) from topping as t
	inner join
		(select * from baseTopping
		where BaseToppingID in 
			(select ToppingCurrentBaseToppingID from toppingCurrent
			where ToppingCurrentPizzaID = pID)) as bt on bt.BaseToppingToppingID = t.ToppingID
	inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID) as double) + 
	cast((select BaseCostCost from baseCost where BaseCostSizeID = sID AND BaseCostCrustID = cID) as double);
end !
delimiter ;
















-- select * from customerOrder;
-- select * from pizza;
select * from topping;
select * from baseTopping;
select * from toppingCurrent;

-- sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter)
select * from topping as t
inner join
	(select * from baseTopping
	where BaseToppingID in 
		(select ToppingCurrentBaseToppingID from toppingCurrent
		where ToppingCurrentPizzaID = 1)) as bt on bt.BaseToppingToppingID = t.ToppingID;
-- inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID;

select ToppingCurrentPizzaID, sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) from toppingCurrent
inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
inner join topping on BaseToppingToppingID = ToppingID
group by ToppingCurrentPizzaID;

-- Change the ToppingCurrentPizzaID for the pizzaID you want
select * from toppingCurrent
inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
inner join topping on BaseToppingToppingID = ToppingID
where ToppingCurrentPizzaID = 1;


select
ToppingCurrentPizzaID, 
sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost, 
sum(ToppingPrice * ToppingCurrentCounter) as TotalToppingPrice
from toppingCurrent
inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
inner join topping on BaseToppingToppingID = ToppingID
where ToppingCurrentPizzaID = 1;

select
ToppingCurrentPizzaID, 
sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost, 
sum(ToppingPrice * ToppingCurrentCounter) as TotalToppingPrice
from toppingCurrent
inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
inner join topping on BaseToppingToppingID = ToppingID
group by ToppingCurrentPizzaID;

select bc.BaseCostCost, bc.BaseCostPrize from pizza as p
inner join baseCost as bc on p.PizzaSizeID = bc.BaseCostSizeID AND p.PizzaCrustID = bc.BaseCostCrustID
where p.PizzaID = 1;



select
cast(
	(select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost
	from toppingCurrent
	inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
	inner join topping on BaseToppingToppingID = ToppingID
	where ToppingCurrentPizzaID = 3) as decimal)
+cast(
	(select bc.BaseCostCost from pizza as p
	inner join baseCost as bc on p.PizzaSizeID = bc.BaseCostSizeID AND p.PizzaCrustID = bc.BaseCostCrustID
	where p.PizzaID = 3) as decimal);
    
select BaseCostCost from baseCost
where
BaseCostCrustID = (select PizzaCrustID from pizza where PizzaID = 10) AND
BaseCostSizeID = (select PizzaSizeID from pizza where PizzaID = 10);

select BaseCostCost from baseCost, (select PizzaCrustID as pcID, PizzaSizeID as psID from pizza where PizzaID = 10) as p
where BaseCostCrustID = p.pcID AND BaseCostSizeID = p.psID;



drop procedure if exists updatePizza;
delimiter !
create procedure updatePizza (IN pID integer)
begin
	update pizza
    set PizzaTotalCost = 
    cast(
		(select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost
		from toppingCurrent
		inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
		inner join topping on BaseToppingToppingID = ToppingID
		where ToppingCurrentPizzaID = pID) as decimal)
	+cast(
		(select BaseCostCost from baseCost
		where
		BaseCostCrustID = (select PizzaCrustID from pizza where PizzaID = pID) AND
		BaseCostSizeID = (select PizzaSizeID from pizza where PizzaID = pID)) as decimal);
end !
delimiter ;

drop procedure if exists updatePizza;
delimiter !
create procedure updatePizza (IN pID integer)
begin
	update pizza
    set PizzaTotalCost = 
    cast(
		(select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost
		from toppingCurrent
		inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
		inner join topping on BaseToppingToppingID = ToppingID
		where ToppingCurrentPizzaID = pID) as decimal)
	+cast(
		(select BaseCostCost from baseCost, (select PizzaCrustID as pcID, PizzaSizeID as psID from pizza where PizzaID = 10) as p
		 where BaseCostCrustID = p.pcID AND BaseCostSizeID = p.psID) as decimal);
end !
delimiter ;

/*
delimiter !
create function getPizzaTotalPrice (sID int, cID int, pID int) returns double DETERMINISTIC NO SQL READS SQL DATA
begin
	return
    (cast((select sum(ToppingPrice * ToppingCurrentCounter) from topping as t
	inner join
		(select * from baseTopping
		where BaseToppingID in 
			(select ToppingCurrentBaseToppingID from toppingCurrent
			where ToppingCurrentPizzaID = pID)) as bt on bt.BaseToppingToppingID = t.ToppingID
	inner join toppingCurrent as tc on tc.ToppingCurrentBaseToppingID = BaseToppingID) as double) + 
	cast((select BaseCostPrize from baseCost where BaseCostSizeID = sID AND BaseCostCrustID = cID) as double));
end !
delimiter ;
*/



select sum(d.DiscountValue) from orderDiscount as od
inner join discount as d on d.DiscountID = od.OrderDiscountDiscountID 
where od.OrderDiscountOrderID = 2 AND d.DiscountIsPercent = FALSE; 

select cast((select sum(d.DiscountValue) from orderDiscount as od
										inner join discount as d on d.DiscountID = od.OrderDiscountDiscountID 
										where od.OrderDiscountOrderID = 1 AND d.DiscountIsPercent = TRUE) as decimal(8,2));
                                        
select *, sum(d.DiscountValue) from pizzaDiscount as pd
inner join discount as d on d.DiscountID = pd.PizzaDiscountDiscountID
group by d.DiscountIsPercent;
-- where pd.PizzaDiscountPizzaID = 7

select d.DiscountIsPercent
from pizzaDiscount as pd
inner join pizza as p on pd.PizzaDiscountPizzaID = p.PizzaID
inner join customerOrder as co on p.PizzaOrderID = co.CustomerOrderID
inner join discount as d on pd.PizzaDiscountDiscountID = d.DiscountID
where PizzaID = 11 and CustomerOrderID = 4;



select count(*) from pizza where PizzaOrderID = 2;


/*
Wrong Pizza proce and Cost
3 Both
10 Cost
11 Cost
12 Cost
13 Price
14 Both
15 Cost
16 Cost
*/



