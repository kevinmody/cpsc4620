-- Sahil Patel and Kevin Mody

use Pizzeria;

drop user JavaMan;
create user if not exists JavaMan identified by "SahilKevin";
grant SELECT, INSERT, UPDATE on Pizzeria.* to JavaMan;


insert into size(SizeType) values
("Small"), ("Medium"), ("Large"), ("X-Large");

insert into crust(CrustType) values
("Thin"), ("Original"), ("Pan"), ("Gluten-Free");

insert into baseCost(BaseCostSizeID, BaseCostCrustID, BaseCostPrize, BaseCostCost) values
(1,1,3, 0.5), (1,2,3, 0.75), (1,3,3.5,1), (1,4,4,2),
(2,1,5, 1), (2,2,5, 1.5), (2,3,6, 2.25), (2,4,6.25, 3),
(3,1,8,1.25), (3,2,8,2), (3,3,9,3), (3,4,9.5,4),
(4,1,10,2), (4,2,10,3), (4,3,11.5,4.5), (4,4,12.5,6);

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
("Four Cheese Blend", 2, 0.15, 150),
("Feta Cheese", 2, 0.18, 75),
("Goat Cheese", 2, 0.2, 54),
("Bacon", 1.5, 0.25, 89);

insert into baseTopping(BaseToppingToppingID, BaseToppingSizeID, BaseToppingUnit) values
(1, 1, 2), (1, 2, 2.75), (1, 3, 3.5), (1, 4, 4.5),
(2, 1, 2.5), (2, 2, 3), (2, 3, 3.5), (2, 4, 4.25),
(3, 1, 2), (3, 2, 2.5), (3, 3, 3.25), (3, 4, 4),
(4, 1, 1.5), (4, 2, 2), (4, 3, 2.25), (4, 4, 3),
(5, 1, 1), (5, 2, 1.5), (5, 3, 2), (5, 4, 2.5),
(6, 1, 1), (6, 2, 1.5), (6, 3, 2), (6, 4, 2.75),
(7, 1, 2), (7, 2, 3), (7, 3, 3.5), (7, 4, 4.5),
(8, 1, 1.5), (8, 2, 2), (8, 3, 2.5), (8, 4, 3),
(9, 1, 0.75), (9, 2, 1), (9, 3, 1.5), (9, 4, 2),
(10, 1, 1), (10, 2, 1.25), (10, 3, 1.75), (10, 4, 2),
(11, 1, 0.5), (11, 2, 0.75), (11, 3, 1.25), (11, 4, 1.75),
(12, 1, 0.6), (12, 2, 1), (12, 3, 1.3), (12, 4, 1.75),
(13, 1, 2), (13, 2, 3.5), (13, 3, 5), (13, 4, 7),
(14, 1, 2), (14, 2, 3.5), (14, 3, 5), (14, 4, 7),
(15, 1, 1.75), (15, 2, 3), (15, 3, 4), (15, 4, 5.5),
(16, 1, 1.6), (16, 2, 2.75), (16, 3, 4), (16, 4, 5.5),
(17, 1, 1), (17, 2, 1.5), (17, 3, 2), (17, 4, 3);


insert into discount(DiscountName, DiscountValue, DiscountIsPercent, DiscountOnOrder) values
("Employee", 15, True, True),
("Lunch Special Medium", 1.00, False, False),
("Lunch Special Large", 2.00, False, False),
("Specialty Pizza", 1.50, False, False),
("Gameday Special", 20, True, True);

-- Dummy Customer for Dine-in orders
insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (0, "DineIn", "DineIn", "9999999999");

-- 1st Order
/*
On March 5th at 12:03 pm there was a dine-in order for a large thin crust pizza with Regular Cheese
(extra), Pepperoni, and Sausage (Price: 13.50, Cost: 3.68 ). They used the “Lunch Special Large” discount
They sat at Table 14.
*/

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values (1,0, "DineIn", "2022-03-05 12:03:00", 0 ,0, True);
insert into dineIn(DineInCustomerOrderID, DineInTableNumber)
values (1, 14);
insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost)
values(1, 1, 3, 1, "Complete", 13.50, 3.68);
insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values (1, 51, 2) , (1, 3, 1), (1, 7, 1);
insert into pizzaDiscount(PizzaDiscountPizzaID, PizzaDiscountDiscountID)
values (1, 3);

call updateCustomerOrder(1);
call applyDiscount(1);


-- Order 2 
/*
On April 3 rd at 12:05 pm there was a dine-in order At table 4. They ordered a medium pan pizza with
Feta Cheese, Black Olives, Roma Tomatoes, Mushrooms and Banana Peppers (P: 10.60, C: 3.23). They get
the “Lunch Special Medium” and the “Specialty Pizza” discounts. They also ordered a small original crust
pizza with Regular Cheese, Chicken and Banana Peppers (P: 6.75, C: 1.40).
*/

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete) values
(2,0, "DineIn", "2022-04-03 12:05:00", 0, 0, True);
insert into dineIn(DineInCustomerOrderID, DineInTableNumber) values
(2, 4);
insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost) values
(2, 3, 2, 2, "Complete", 10.60, 3.23),
(3, 2, 1, 2, "Complete", 6.75, 1.40);
insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(2, 58, 1), (2, 34, 1), (2, 26, 1), (2, 30, 1), (2, 46, 1),
(3, 49, 1), (3, 13, 1), (3, 45, 1);
insert into orderDiscount(OrderDiscountOrderID, OrderDiscountDiscountID) values
(2, 2), (2, 4);

call updateCustomerOrder(2);
call applyDiscount(2);


-- Order 3
/*
On March 3 rd at 9:30 pm Andrew Wilkes-Krier places an order for pickup of 6 large original crust pizzas
with Regular Cheese and Pepperoni (Price: 10.75, Cost:3.30 each). Andrew’s phone number is 864-254-
5861.
*/


insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (1, "Andrew", "Wilkes-Krier", "8642545861");
insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values(3,1, "PickUp", "2022-03-03 09:30:00", 0, 0, True);
insert into pickup(PickupCustomerOrderID, PickupTimestamp)
values(3, "2022-03-03 09:50:00");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost) values
(4, 2, 3, 3, "Complete", 10.75, 3.30),
(5, 2, 3, 3, "Complete", 10.75, 3.30),
(6, 2, 3, 3, "Complete", 10.75, 3.30),
(7, 2, 3, 3, "Complete", 10.75, 3.30),
(8, 2, 3, 3, "Complete", 10.75, 3.30),
(9, 2, 3, 3, "Complete", 10.75, 3.30);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(4, 51, 1), (4, 3, 1),
(5, 51, 1), (5, 3, 1),
(6, 51, 1), (6, 3, 1),
(7, 51, 1), (7, 3, 1),
(8, 51, 1), (8, 3, 1),
(9, 51, 1), (9, 3, 1);

call updateCustomerOrder(3);
call applyDiscount(3);


-- Order 4
/*
On April 20 th at 7:11 pm there was a delivery order made by Andrew Wilkes-Krier for 1 x-large pepperoni
and Sausage pizza (P 14.50, C 5.59), one x-large pizza with Ham (extra) and Pineapple (extra) pizza (P: 17,
C: 5.59), and one x-large Jalapeno and Bacon pizza (P: 14.00, C: 5.68). All the pizzas have the Four Cheese
Blend on it and are original crust. The order has the “Gameday Special” discount applied to it, and the
ham and pineapple pizza has the “Specialty Pizza” discount applied to it. The pizzas were delivered to 115
Party Blvd, Anderson SC 29621. His phone number is the same as before.
*/

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values(4,1,"Delivery","2022-04-20 19:11:00", 0, 0, True);
insert into delivery(DeliveryCustomerOrderID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(4, "115 Party Blvd", "Anderson", "SC", "29621");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost) values
(10, 2, 4, 4, "Complete", 14.50, 5.59),
(11, 2, 4, 4, "Complete", 17.00, 5.59),
(12, 2, 4, 4, "Complete", 14.00, 5.68);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(10, 4, 1), (10,8,1), (10, 56,1),
(11, 12, 2),(11, 40, 2), (11, 56,1),
(12, 44, 1),(12, 68, 1), (12, 56,1);

insert into orderDiscount(OrderDiscountOrderID, OrderDiscountDiscountID) values
(4, 5);
insert into pizzaDiscount(PizzaDiscountPizzaID, PizzaDiscountDiscountID) values
(11, 4);

call updateCustomerOrder(4);
call applyDiscount(4);


-- Order 5
/*
On March 2 nd at 5:30 pm Matt Engers placed an order for pickup for an x-large pizza with Green Pepper,
Onion, Roma Tomatoes, Mushrooms, and Black Olives on it. He wants the Goat Cheese on it, and a Gluten
Free Crust (P: 16.85, C:7.85). The “Specialty Pizza” discount is applied to the pizza. Matt’s phone number
is 864-474-9953.
*/

insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (2, "Matt", "Engers", "8644749953");

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values(5,2, "PickUp", "2022-03-02 17:30:00", 0, 0, true);

insert into pickup(PickupCustomerOrderID, PickupTimestamp)
values(5, "2022-03-02 17:50:00");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost)
values(13, 4, 4, 5, "Complete", 16.85, 7.85);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values (13,64,1), (13, 20, 1), (13, 24, 1), (13, 28, 1), (13, 32, 1), (13, 36, 1);

insert into pizzaDiscount(PizzaDiscountPizzaID, PizzaDiscountDiscountID) values
(13, 4);

call updateCustomerOrder(5);
call applyDiscount(5);


-- Order 6
/*
On March 2 nd at 6:17 pm Frank Turner places on order for delivery of one large pizza with Chicken, Green
Peppers, Onions, and Mushrooms. He wants the Four Cheese Blend (extra) and thin crust (P: 13.25, C:
3.20). The pizza was delivered to 6745 Wessex St Anderson SC 29621. Frank’s phone number is 864-232-
8944.
*/

insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (3, "Frank", "Turner", "8642328944");

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values(6,3, "Delivery", "2022-03-02 06:17:00", 0, 0, true);

insert into delivery(DeliveryCustomerOrderID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(6, "6745 Wessex St", "Anderson", "SC", "29621");


insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost)
values(14, 1, 3, 6, "Complete", 13.25, 3.20);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter)
values(14, 15, 1),(14, 19, 1), (14,23,1), (14,31,1), (14,55,2);

call updateCustomerOrder(6);
call applyDiscount(6);


-- Order 7
/*
On April 13 th at 8:32 pm Milo Auckerman ordered two large thin crust pizzas. One had the Four Cheese
Blend on it (extra) (P: 12, C: 3.75), the other was Regular Cheese and Pepperoni (extra) (P:12, C: 2.55). He
used the “Employee” discount on his order. He had them delivered to 8879 Suburban Home, Anderson,
SC 29621. His phone number is 864-878-5679.
*/

insert into customer(CustomerID, CustomerFname, CustomerLname, CustomerPhone)
values (4, "Milo", "Auckerman", "8648785679");

insert into customerOrder(CustomerOrderID, CustomerOrderCustomerID, CustomerOrderType, CustomerOrderTimeStamp, CustomerOrderTotalprice, CustomerOrderTotalcost, CustomerOrderIsComplete)
values(7,4,"Delivery","2022-04-13 08:32:00", 0, 0, true);

insert into delivery(DeliveryCustomerOrderID, DeliveryStreet, DeliveryCity, DeliveryState, DeliveryZip)
values(7, "879 Suburban Home", "Anderson", "SC", "29621");

insert into pizza(PizzaID, PizzaCrustID, PizzaSizeID, PizzaOrderID, PizzaState, PizzaTotalPrice, PizzaTotalCost) values
(15, 1, 3, 7, "Complete", 12.00, 3.75),
(16, 1, 3, 7, "Complete", 12.00, 2.55);

insert into toppingCurrent(ToppingCurrentPizzaID, ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
(15, 55, 2),
(16, 51, 1), (16, 3, 2);

insert into orderDiscount(OrderDiscountOrderID, OrderDiscountDiscountID) values
(7, 1);

call updateCustomerOrder(7);
call applyDiscount(7);
