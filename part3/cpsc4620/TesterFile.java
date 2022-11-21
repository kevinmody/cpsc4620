package cpsc4620;

import java.io.IOException;
import java.sql.SQLException;

public class TesterFile {
    public static void main(){
        DeliveryOrder delOrder = new DeliveryOrder(1, 1, "2020", 0.00, 0.00, 0, "Address");
        try {
            DBNinja.addOrder(delOrder);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}
