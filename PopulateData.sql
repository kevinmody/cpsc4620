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
("Lunch Special Medium", 1.00, False, True),
("Lunch Special Large", 2.00, False, True),
("Specialty Pizza", 1.50, False, True),
("Gameday Special", 20, True, True);


/*

insert into toppingCurrent(ToppingCurrentBaseToppingID, ToppingCurrentCounter) values
()
insert into pizza(PizzaCrustID, PizzaSizeID, PizzaTopCurrentID, PizzaOrderID, PizzaDiscountID, PizzaState, PizzaTotalCost, PizzaTotalPrice)
values(3, 1, 
insert into customerOrder(

*/