package Repository;
import Model.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class OrderRepository extends BaseRepository {

    public OrderRepository() {
    }

    public Integer addToCart(int customerId, Integer orderId, int productOptionId) {
        try (CallableStatement stmt = connection.prepareCall("call AddToCart(?,?,?)")) {
            stmt.setInt(1, customerId);
            if (orderId == null) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, orderId);
            }
            stmt.setInt(3, productOptionId);
            stmt.execute();
            orderId = stmt.getInt(2);
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid tillägg av varor till kundvagn: " + e.getMessage());
        }
        return orderId;
    }

    public List<CustomerOrder> getCustomerOrderData() {
        List<CustomerOrder> customerOrders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "select order_id, order_date, order_status, total_price, customer_id from CustomerOrder")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CustomerOrder customerOrder = new CustomerOrder(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getString("order_status"),
                        rs.getDouble("total_price"),
                        rs.getInt("customer_id"));
                customerOrders.add(customerOrder);
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid hämtning av orderdata " + e.getMessage());
        }
        return customerOrders;
    }

    public OrderItem getOrderItemData(int orderId) {
        OrderItem orderItem = null;

        try (PreparedStatement stmt = connection.prepareStatement(
                "select order_item_id, order_item_quantity, product_option_id from OrderItem where order_id = ?")) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idItem = rs.getInt("order_item_id");
                int quantity = rs.getInt("order_item_quantity");
                int idOption = rs.getInt("product_option_id");
                orderItem = new OrderItem(idItem, quantity, idOption, orderId);
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid hämtning av ordervaror " + e.getMessage());
        }
        return orderItem;
    }

    public Integer createNewOrder(int customerId) {
        // SQL-fråga för att skapa en ny order
        String sql = "INSERT INTO CustomerOrders (customer_id, order_status) VALUES (?, ?)";

        // Använd BaseRepository för att exekvera frågan
        try {
            // Förbered statementet för att exekvera frågan
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, customerId);
                statement.setString(2, "aktiv"); // Standard status "aktiv"

                // Exekvera uppdateringen och kontrollera att en rad påverkas
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    // Om en rad påverkades, hämta det genererade orderId
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);  // Returnera det genererade orderId
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Fel vid skapande av ny order: " + e.getMessage());
        }

        // Om något går fel eller ingen rad påverkas, returnera null
        return null;
    }


}