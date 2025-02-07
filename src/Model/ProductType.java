package Model;

public class ProductType {

    private int productId;
    private String productTypeName;

    public ProductType() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public ProductType(int productId, String productTypeName) {
        this.productId = productId;
        this.productTypeName = productTypeName;
    }

    @Override
    public String toString() {
        return productTypeName;
    }
}
