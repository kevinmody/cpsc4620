-- Sahil Patel and Kevin Mody

use Pizzeria;
insert into size(SizeType) values
("Small"),
("Medium"),
("Large"),
("X-Large");

insert into crust(CrustType) values
("Thin"),
("Original"),
("Pan"),
("Gluten-Free");

insert into baseCost(BaseCostSizeID, BaseCostCrustID, BaseCostPrize, BaseCostCost) values
(1,1,3, 0.5),
(1,2,3, 0.75),
(1,3,3.5,1),
(1,4,4,2),
(2,1,5, 1),
(2,2,5, 1.5),
(2,3,6, 2.25),
(2,4,6.25, 3),
(3,1,8,1.25),
(3,2,8,2),
(3,3,9,3),
(3,4,9.5,4),
(4,1,10,2),
(4,2,10,3),
(4,3,11.5,4.5),
(4,4,12.5,6);

insert into topping(ToppingName, ToppingPrice, ToppingCost, ToppingCurrentInventory) values
("Pepperoni", 1.25, 0.2, 100),
("Sausage", 1.25, 0.15, 100),
("Ham", 1.5, 0.15, 78),
("Chicken", 1.75, 0.25, 56),
("Green Pepper", 0.5, 0.02, 79),
("Onion", 0.5, 0.02, 85),
("Roma Tomato ", 0.75, 0.03, 86),
("Mushroom", 0.75, 0.1, 52),
("Black Olive", 0.6, 0.1, 39),
("Pineapple", 1, 0.25, 15),
("Jalapeno", 0.5, 0.05, 64),
("Banana Pepper", 0.5, 0.05, 36),
("Regular Cheese", 1.5, 0.12, 250),
("Four Cheese Blend", 2, 1.5, 150),
("Feta Cheese", 2, 0.18, 75),
("Goat Cheese", 2, 0.2, 54),
("Bacon", 1.5, 0.25, 89);

insert into baseTopping(BaseToppingToppingID, BaseToppingSizeID, BaseToppingUnit) values
(1, 1, 2),
(1, 2, 2.75),
(1, 3, 3.5),
(1, 4, 4.5),
(2, 1, 2.5),
(2, 2, 3),
(2, 3, 3.5),
(2, 4, 4.25),
(3, 1, 2),
(3, 2, 2.5),
(3, 3, 3.25),
(3, 4, 4),
(4, 1, 1.5),
(4, 2, 2),
(4, 3, 2.25),
(4, 4, 3),
(5, 1, 1),
(5, 2, 1.5),
(5, 3, 2),
(5, 4, 2.5),
(6, 1, 1),
(6, 2, 1.5),
(6, 3, 2),
(6, 4, 2.75),
(7, 1, 2),
(7, 2, 3),
(7, 3, 3.5),
(7, 4, 4.5),
(8, 1, 1.5),
(8, 2, 2),
(8, 3, 2.5),
(8, 4, 3),
(9, 1, 0.75),
(9, 2, 1),
(9, 3, 1.5),
(9, 4, 2),
(10, 1, 1),
(10, 2, 1.25),
(10, 3, 1.75),
(10, 4, 2),
(11, 1, 0.5),
(11, 2, 0.75),
(11, 3, 1.25),
(11, 4, 1.75),
(12, 1, 0.6),
(12, 2, 1),
(12, 3, 1.3),
(12, 4, 1.75),
(13, 1, 2),
(13, 2, 3.5),
(13, 3, 5),
(13, 4, 7),
(14, 1, 2),
(14, 2, 3.5),
(14, 3, 5),
(14, 4, 7),
(15, 1, 1.75),
(15, 2, 3),
(15, 3, 4),
(15, 4, 5.5),
(16, 1, 1.6),
(16, 2, 2.75),
(16, 3, 4),
(16, 4, 5.5),
(17, 1, 1),
(17, 2, 1.5),
(17, 3, 2),
(17, 4, 3);

insert into discount(DiscountName, DiscountValue, DiscountIsPercent, DiscountOnOrder) values
("Employee", 15, True, True),
("Lunch Special Medium", 1.00, False, False),
("Lunch Special Large", 2.00, False, False),
("Specialty Pizza", 1.50, False, False),
("Gameday Special", 20, True, True);


-- 1st Order
insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values (1, "DineIn", "2022-03-05 12:03:00", 0 ,0);
insert into dineIn(DineInCustomerOrderID, DineInTableNumber)
values (1, 14);
insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice)
values(1, 1, 3, 1, "Complete", 0, 0);
insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values (1, 51, 2) , (1, 3, 1), (1, 7, 1);

call updatePizza(1);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 1) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 1) as double)
where CustomerOrderID = 1;



-- Order 2 
insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values (2, "DineIn", "2022-04-03 12:05:00", 0, 0);
insert into dineIn(DineInCustomerOrderID, DineInTableNumber)
values (2, 4);
insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice)
values
(2, 3, 2, 2, "Complete", 0, 0),
(3, 2, 1, 2, "Complete", 0, 0);
insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values
(2, 58, 1), (2, 34, 1), (2, 26, 1), (2, 30, 1), (2, 46, 1),
(3, 49, 1), (3, 13, 1), (3, 1, 1);

call updatePizza(2);
call updatePizza(3);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 2) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 2) as double)
where CustomerOrderID = 2;



-- Order 3

insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values(3, "PickUp", "2022-03-03 09:30:00", 0, 0);
insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (1, "Andrew", "Wilkes-Krier", "8642545861");
insert into pickup(PickupCustomerOrderID, PickupCustomerID, PickupTimestamp)
values(3, 1, "2022-03-03 09:50:00");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice) values
(4, 2, 3, 3, "Complete", 0, 0),
(5, 2, 3, 3, "Complete", 0, 0),
(6, 2, 3, 3, "Complete", 0, 0),
(7, 2, 3, 3, "Complete", 0, 0),
(8, 2, 3, 3, "Complete", 0, 0),
(9, 2, 3, 3, "Complete", 0, 0);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(4, 51, 1), (4, 3, 1),
(5, 51, 1), (5, 3, 1),
(6, 51, 1), (6, 3, 1),
(7, 51, 1), (7, 3, 1),
(8, 51, 1), (8, 3, 1),
(9, 51, 1), (9, 3, 1);

call updatePizza(4);
call updatePizza(5);
call updatePizza(6);
call updatePizza(7);
call updatePizza(8);
call updatePizza(9);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 3) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 3) as double)
where CustomerOrderID = 3;


-- Order 4

insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values(4,"Delivery","2022-04-20 19:11:00", 0, 0);
insert into delivery(DeliveryCustomerOrderID, DeliveryCustomerID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(4, 1, "115 Party Blvd", "Anderson", "SC", "29621");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice) values
(10, 2, 4, 4, "Complete", 0, 0),
(11, 2, 4, 4, "Complete", 0, 0),
(12, 2, 4, 4, "Complete", 0, 0);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(10, 4, 1), (10,8,1), (10, 56,1),
(11, 12, 2),(11, 40, 2), (11, 56,1),
(12, 44, 1),(12, 68, 1), (12, 56,1);

call updatePizza(10);
call updatePizza(11);
call updatePizza(12);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 4) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 4) as double)
where CustomerOrderID = 4;


-- Order 5

insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values(5, "PickUp", "2022-03-02 17:30:00", 0, 0);
insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (2, "Matt", "Engers", "8644749953");
insert into pickup(PickupCustomerOrderID, PickupCustomerID, PickupTimestamp)
values(5, 2, "2022-03-02 17:50:00");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice)
values(13, 4, 4, 5, "Complete", 0, 0);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values (13,64,1), (13, 20, 1), (13, 24, 1), (13, 28, 1), (13, 32, 1), (13, 36, 1);

call updatePizza(13);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 5) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 5) as double)
where CustomerOrderID = 5;


-- Order 6

insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values(6, "Delivery", "2022-03-02 06:17:00", 0, 0);
insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (3, "Frank", "Turner", "8642328944");
insert into delivery(DeliveryCustomerOrderID, DeliveryCustomerID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(6, 3, "6745 Wessex St", "Anderson", "SC", "29621");


insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice)
values(14, 1, 3, 6, "Complete", 0, 0);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values(14, 15, 1),(14, 19, 1), (14,23,1), (14,31,1), (14,55,2);

call updatePizza(14);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 6) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 6) as double)
where CustomerOrderID = 6;


-- Order 7

insert into customerOrder(CustomerOrderID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost)
values(7,"Delivery","2022-04-13 08:32:00", 0, 0);
insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (4, "Milo", "Auckerman", "8648785679");
insert into delivery(DeliveryCustomerOrderID, DeliveryCustomerID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(7, 4, "879 Suburban Home", "Anderson", "SC", "29621");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalCost, PizzaTotalPrice) values
(15, 1, 3, 7, "Complete", 0, 0),
(16, 1, 3, 7, "Complete", 0, 0);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(15, 55, 2),
(16, 51, 1), (16, 3, 2);

call updatePizza(15);
call updatePizza(16);

update customerOrder
set CustomerOrderTotalprice = cast((select sum(PizzaTotalPrice) from pizza where PizzaOrderID = 7) as double),
CustomerOrderTotalcost = cast((select sum(PizzaTotalCost) from pizza where PizzaOrderID = 7) as double)
where CustomerOrderID = 7;








drop procedure if exists updatePizza;
delimiter !
create procedure updatePizza (IN pID integer)
begin
	update pizza
    
-- Updating Total Cost
    set PizzaTotalCost = 
    cast(
		(select sum(ToppingCost * BaseToppingUnit * ToppingCurrentCounter) as TotalToppingCost
		from toppingCurrent
		inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
		inner join topping on BaseToppingToppingID = ToppingID
		where ToppingCurrentPizzaID = pID) as decimal)
	+cast(
		(select BaseCostCost from baseCost, (select PizzaCrustID as pcID, PizzaSizeID as psID from pizza where PizzaID = pID) as p
		 where BaseCostCrustID = p.pcID AND BaseCostSizeID = p.psID) as decimal),
         
-- Updating Total Price
     PizzaTotalPrice = 
     cast(
		(select sum(ToppingPrice * ToppingCurrentCounter) as TotalToppingPrice
		from toppingCurrent
		inner join baseTopping on ToppingCurrentBaseToppingID = BaseToppingID
		inner join topping on BaseToppingToppingID = ToppingID
		where ToppingCurrentPizzaID = pID) as decimal)
	+cast(
		(select BaseCostPrize from baseCost, (select PizzaCrustID as pcID, PizzaSizeID as psID from pizza where PizzaID = pID) as p
		 where BaseCostCrustID = p.pcID AND BaseCostSizeID = p.psID) as decimal)
         
-- WHERE clause         
	where PizzaID = pID;
end !
delimiter ;

/*

SET GLOBAL log_bin_trust_function_creators = 1;
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

delimiter !
create function getPizzaTotalCost (sID int, cID int, pID int) returns double READS SQL DATA DETERMINISTIC
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

*/
