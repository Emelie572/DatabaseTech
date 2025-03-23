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

}