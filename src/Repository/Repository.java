package Repository;

import Model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    Properties p = new Properties();

    public Repository() {

        try {
            p.load(new FileInputStream("src/settings.properties"));

        } catch (FileNotFoundException e) {
            System.out.println("Filen hittades ej " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Fel vid läsning av fil" + e.getMessage());
        }

    }


    public Customer getLoginData(String username, String password) {

        Customer customer;

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement(
                     "select customer_id, user_name, first_name, last_name from Customer where user_name = ? and user_password = ?")
        ) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setUserName(rs.getString("user_name"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                return customer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod  vid inloggning " + e.getMessage());
            return null;
        }
    }


    public ProductType getProductTypeData() {

        ProductType productType = null;

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement(
                     "select product_type_id, product_type_name from ProductType")
        ) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("product_type_id");
                String name = rs.getString("product_type_name");
                productType = new ProductType(id, name);

            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid vid hämtning av produkttyp " + e.getMessage());
        }
        return productType;
    }

    List<ProductCategory> getCategoryData(String productTypeName) {

        List<ProductCategory> categories = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select product_category_id, product_category_name from ProductCategory " +
                     "join ProductType on ProductCategory.product_type_id = ProductType.product_type_id " +
                     "where ProductType.product_type_name = ?")


        ) {

            stmt.setString(1, productTypeName);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name"));
                categories.add(productCategory);
            }

        } catch (SQLException e) {
            System.out.println("Fel uppstod vid vid hämtning av skokategorier " + e.getMessage());
        }
        return categories;
    }


    List<Product> getProductByCategoryData(String productCategoryName) {

        List<Product> productsByCategory = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select Product.product_id, Product.product_name, Product.product_price from Product " +
                     "join BelongTo on Product.product_id = BelongTo.product_id  " +
                     "join ProductCategory on BelongTo.product_category_id = ProductCategory.product_category_id " +
                     "where product_category_name = ?")


        ) {

            stmt.setString(1, productCategoryName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product products = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("product_price"));
                productsByCategory.add(products);
            }

        } catch (SQLException e) {
            System.out.println("Fel uppstod vid vid hämtning av produkter " + e.getMessage());
        }

        return productsByCategory;
    }


    List<ProductOption> getProductOptionData(String productName) {

        List<ProductOption> productOptions = new ArrayList<>();


        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select ProductOption.product_option_id, ProductOption.size, ProductOption.color from ProductOption " +
                     "join Product on ProductOption.product_id = Product.product_id " +
                     "where Product.product_name = ?")

        ) {

            stmt.setString(1, productName);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductOption productOption = new ProductOption();

                productOption.setProductOptionId(rs.getInt("product_option_id"));
                productOption.setSize(rs.getInt("size"));
                productOption.setColor(rs.getString("color"));

                productOptions.add(productOption);

            }

        } catch (SQLException e) {
            System.out.println("Fel uppstod vid vid hämtning av Produktval " + e.getMessage());
        }
        return productOptions;
    }


    public List<CustomerOrder> getCustomerOrderData() {

        List<CustomerOrder> customerOrders = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select order_id, order_date, order_status, total_price, customer_id from CustomerOrder")

        ) {

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


    public Integer addToCart(int customerId, Integer orderId, int productOptionId) {

        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             CallableStatement stmt = c.prepareCall("call AddToCart(?,?,?)")

        ) {

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
            System.out.println("Fel uppstod  " + e.getMessage());
        }

        return orderId;
    }


    public OrderItem getOrderItemData(int orderId) {

        OrderItem orderItem = null;


        try (Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement stmt = c.prepareStatement("select order_item_id, order_item_quantity, product_option_id from OrderItem where order_id = ?")

        ) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                int idItem = rs.getInt("order_item_id");
                int quantity = rs.getInt("order_item_quantity");
                int idOption = rs.getInt("product_option_id");
                int idOrder = rs.getInt("order_id");

                orderItem = new OrderItem(idItem, quantity, idOption, idOrder);
            }


        } catch (SQLException e) {
            System.out.println("Fel uppstod vid hämtning av ordervaror " + e.getMessage());
            return null;
        }

        return orderItem;
    }
}