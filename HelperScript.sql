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



