select * from size;
select * from crust;
select * from baseCost;
select * from topping;
select * from discount;
select * from baseTopping;
select * from customerOrder;

select bt.BaseToppingID, size.SizeType, topping.ToppingName, bt.BaseToppingUnit
from baseTopping as bt
inner join size on bt.BaseToppingSizeID = size.SizeID
inner join topping on bt.BaseToppingToppingID = topping.ToppingID
order by bt.BaseToppingID;

/*
select * from ;
select * from ;
select * from ;
select * from ;
select * from ;
select * from ;
select * from ;
select * from ;
*/