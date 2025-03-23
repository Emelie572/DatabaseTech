package Repository;

import Model.Product;
import Model.ProductCategory;
import Model.ProductOption;
import Model.ProductType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends BaseRepository{


    public ProductRepository() {
    }

    public List<ProductType> getProductTypeData() {
        List<ProductType> productTypes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "select product_type_id, product_type_name from ProductType")) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ProductType productType = new ProductType(
                rs.getInt("product_type_id"),
                rs.getString("product_type_name"));
                productTypes.add(productType);
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid hämtning av produkttyp " + e.getMessage());
        }
        return productTypes;
    }

    public List<ProductCategory> getCategoryData(String productTypeName) {
        List<ProductCategory> categories = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "select product_category_id, product_category_name from ProductCategory " +
                        "join ProductType on ProductCategory.product_type_id = ProductType.product_type_id " +
                        "where ProductType.product_type_name = ?")) {

            stmt.setString(1, productTypeName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductCategory productCategory = new ProductCategory(
                        rs.getInt("product_category_id"),
                        rs.getString("product_category_name"));
                categories.add(productCategory);
            }
        } catch (SQLException e) {
            System.out.println("Fel uppstod vid hämtning av skokategorier " + e.getMessage());
        }
        return categories;
    }

    public List<Product> getProductByCategoryData(String productCategoryName) {
        List<Product> productsByCategory = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "select Product.product_id, Product.product_name, Product.product_price from Product " +
                        "join BelongTo on Product.product_id = BelongTo.product_id  " +
                        "join ProductCategory on BelongTo.product_category_id = ProductCategory.product_category_id " +
                        "where product_category_name = ?")) {

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
            System.out.println("Fel uppstod vid hämtning av produkter " + e.getMessage());
        }
        return productsByCategory;
    }

    public List<ProductOption> getProductOptionData(String productName) {
        List<ProductOption> productOptions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "select ProductOption.product_option_id, ProductOption.size, ProductOption.color from ProductOption " +
                        "join Product on ProductOption.product_id = Product.product_id " +
                        "where Product.product_name = ?")) {

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
            System.out.println("Fel uppstod vid hämtning av Produktval " + e.getMessage());
        }
        return productOptions;
    }

}
