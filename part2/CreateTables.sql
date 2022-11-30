-- Sahil Patel and Kevin Mody

use Pizzeria;
create table size (
	SizeID Integer primary key auto_increment,
    SizeType varchar(255) not null unique,
    SizeStaticCounter Integer not null default 0
);

create table crust (
	CrustID Integer primary key auto_increment,
    CrustType varchar(255) not null unique,
    CrustStaticCounter Integer not null default 0
);

create table baseCost (
	BaseCostSizeID Integer,
    BaseCostCrustID Integer,
    primary key (BaseCostSizeID, BaseCostCrustID),
    BaseCostCost decimal(6,2) not null default 0.00,
	foreign key (BaseCostSizeID) references size(SizeID) on update cascade,
    BaseCostPrize decimal(6,2) not null default 0.00,
    foreign key (BaseCostCrustID) references crust(CrustID) on update cascade
);

create table topping (
	ToppingID Integer primary key auto_increment,
    ToppingName varchar(255) not null unique,
    ToppingCost decimal(6,2) not null default 0.00,
    ToppingPrice decimal(6,2) not null default 0.00,
    ToppingCurrentInventory decimal(6,4) not null default 0.0,
    ToppingMinInventory decimal(6,4) not null default 10.0,
    ToppingStaticCounter Integer not null default 0
);

create table baseTopping (
	BaseToppingID integer auto_increment not null unique,
	BaseToppingToppingID Integer,
    foreign key (BaseToppingToppingID) references topping(ToppingID) on update cascade, 
    BaseToppingSizeID Integer,
    foreign key (BaseToppingSizeID) references size(SizeID) on update cascade,
    primary key (BaseToppingToppingID, BaseToppingSizeID),
    BaseToppingUnit double(3,2) not null default 0.00
);

create table customer (
	CustomerID Integer primary key,
    CustomerFname varchar(255) not null unique,
    CustomerLname varchar(255) not null unique, 
    CustomerPhone varchar(10)
);

create table discount (
	DiscountID int primary key auto_increment,
    DiscountName varchar(255) unique,
    DiscountValue decimal(6,2) not null,
    DiscountIsPercent boolean not null,
    DiscountOnOrder boolean not null
);

create table customerOrder (
	CustomerOrderID integer not null primary key auto_increment, 
	CustomerOrderCustomerID integer not null,
	foreign key (CustomerOrderCustomerID) references customer(CustomerID),
    CustomerOrderType varchar(255) not null check(CustomerOrderType in ("DineIn", "Pickup", "Delivery")),
    CustomerOrderTimeStamp timestamp not null,
    CustomerOrderTotalCost double(8,2) not null default 0.0,
    CustomerOrderTotalPrice double(8,2) not null default 0.0,
	CustomerOrderIsComplete boolean not null default false
);
create table dineIn (
	DineInCustomerOrderID integer not null primary key,
    foreign key (DineInCustomerOrderID) references customerOrder(CustomerOrderID),
    DineInTableNumber integer not null
);
create table pickup (
	PickupCustomerOrderID integer not null primary key,
    foreign key (PickupCustomerOrderID) references customerOrder(CustomerOrderID),
    PickupTimestamp timestamp not null
);
create table delivery (
	DeliveryCustomerOrderID integer not null primary key,
    foreign key (DeliveryCustomerOrderID) references customerOrder(CustomerOrderID),
    DeliveryStreet varchar(255) not null,
    DeliveryCity varchar(255) not null,
    DeliveryState varchar(2) not null,
    DeliveryZip varchar(6) not null
);

create table pizza(
	PizzaID int not null auto_increment primary key,
	PizzaCrustID int not null,
    PizzaSizeID int not null,
    PizzaOrderID int not null,
    foreign key (PizzaCrustID) references baseCost(BaseCostCrustID),
    foreign key (PizzaSizeID) references baseCost(BaseCostSizeId),
    foreign key (PizzaOrderID) references customerOrder(CustomerOrderID), 
    
    PizzaState varchar(255) not null,
    PizzaTotalCost double(8,2) not null,
    PizzaTotalPrice double(8,2) not null
);

create table toppingCurrent (
	ToppingCurrentID Integer primary key auto_increment,
    ToppingCurrentBaseToppingID Integer,
    foreign key (ToppingCurrentBaseToppingID) references baseTopping(BaseToppingID) on update cascade,
    ToppingCurrentPizzaID int not null,
	foreign key (ToppingCurrentPizzaID) references pizza(PizzaID) on update cascade,
	ToppingCurrentCounter Integer not null default 0
);

create table orderDiscount (
	OrderDiscountID Integer primary key auto_increment,
    OrderDiscountDiscountID integer not null,
    OrderDiscountOrderID integer not null,
    foreign key (OrderDiscountDiscountID) references discount(DiscountID),
    foreign key (OrderDiscountOrderID) references customerOrder(CustomerOrderID)
);

create table pizzaDiscount (
	PizzaDiscountID integer primary key auto_increment,
    PizzaDiscountDiscountID integer not null,
    PizzaDiscountPizzaID integer not null,
    foreign key (PizzaDiscountDiscountID) references discount(DiscountID),
    foreign key (PizzaDiscountPizzaID) references pizza(PizzaID)
);

