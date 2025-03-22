package Model;
import Interface.Name;


public class Product implements Name{

    private int productId;
    private String productName;
    private double productPrice;


    public Product() {
    }

    public Product(int productId, String productName, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String getName() {
        return productName;
    }

    @Override
    public String toString() {
        return getName();
    }


}
