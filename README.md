# 4620 Pizzaria Project
Roger Van Scoy taught this course.

## Details
- We changes almost all files, since we overly complicated database design in part 1 and 2.
- Planned on adding ToppingBase but scratched that idea and added an arraylist that stores UnitSize within Topping.
- We were able to work out with given function definitions.
    - Some had to be modified and a couple function was added because of overall difference in design.

## Thougths/Remarks
- Connection closes if there are nested connected to db calls.
  - addOrder -> getMaxOrderID -> addOrder.
  - Connection closes even if we commented out conn.close.
- getMaxOrderID and getMaxPizzaID might not be needed when inserting into database.
- asking customer to dine but then after entering table # its asks for another pizza eventhough customer havent add any pizza.



Note: On my honor I have neither given nor received aid on this exam nor the projects.
