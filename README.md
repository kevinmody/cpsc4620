# cpsc4620

## Thougths/Remarks
- Connection closes if there are nested connected to db calls
  - addOrder -> getMaxOrderID -> addOrder
  - Connection closes even if we commented out conn.close
- getMaxOrderID and getMaxPizzaID might not be needed when inserting into database
- asking customer to dine but then after entering table # its asks for another pizza eventhough customer havent add any pizza
