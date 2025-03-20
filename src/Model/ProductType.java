package Model;

public class ProductType {

    private final int productId;
    private final String productTypeName;

    public ProductType(int productId, String productTypeName) {
        this.productId = productId;
        this.productTypeName = productTypeName;
    }


    public int getProductId() {
        return productId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }


    @Override
    public String toString() {
        return productTypeName;
    }
}
