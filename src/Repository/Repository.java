package Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

        List<Customer> getCustomers() throws IOException, SQLException {

        Properties p = new Properties();
        p.load(new FileInputStream("src/settings.properties"));

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("select first_name,last_name from Customer"))
        {
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer();

                String first_name = rs.getString("first_name");
                customer.setFirstName(first_name);

                String last_name = rs.getString("last_name");
                customer.setLastName(last_name);

                customers.add(customer);

            }
            customers.forEach(customer -> System.out.println(customer.getFirstName() + " " + customer.getLastName()));
            return customers;
        }

        }


    List<ProductCategory> getCategories(String categoryName) throws IOException, SQLException {

        Properties p = new Properties();
        p.load(new FileInputStream("src/settings.properties"));

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select product_category_id, product_category_name from ProductCategory " +
                     "join ProductType on ProductCategory.product_type_id = ProductType.product_type_id " +
                     "where ProductType.product_type_name = ?")


        ) {

            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            List<ProductCategory> categories = new ArrayList<>();

            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name"));
                categories.add(productCategory);

            }
            return categories;
        }

    }

}