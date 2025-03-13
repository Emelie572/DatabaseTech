package Repository;

import Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository extends BaseRepository{

    public CustomerRepository() {
    }

    public Customer getLoginData(String username, String password) {
        Customer customer = null;

        try (PreparedStatement stmt = connection.prepareStatement(
                "select customer_id, user_name, first_name, last_name from Customer where user_name = ? and user_password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setUserName(rs.getString("user_name"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid inloggning " + e.getMessage());
        }
        return customer;
    }

}
