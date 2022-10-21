create table size (
	SizeID Integer primary key,
    SizeType varchar(255) not null unique,
    SizeStaticCounter Integer not null default 0
);

create table crust (
	CrustID Integer primary key,
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
	ToppingID Integer primary key,
    ToppingName varchar(255) not null unique,
    ToppingCost decimal(6,2) not null default 0.00,
    ToppingPrice decimal(6,2) not null default 0.00,
    ToppingCurrentInventory Integer not null default 0,
    ToppingMinInventory Integer not null default 1,
    ToppingStaticCounter integer not null default 0
);

create table baseToppingCost (
	BaseToppingCostToppingID Integer,
    foreign key (BaseToppingCostToppingID) references topping(ToppingID) on update cascade, 
    BaseToppingCostSizeID Integer,
    foreign key (BaseToppingCostSizeID) references size(SizeID) on update cascade,
    primary key (BaseToppingCostToppingID, BaseToppingCostSizeID),
    BaseToppingCostCost decimal(6,2) not null default 0.00,
    BaseToppingCostPrice decimal(6,2) not null default 0.00
);