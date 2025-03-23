package Model;

import Interface.Name;

public class ProductType implements Name {

    private  int productId;
    private  String productTypeName;

    public ProductType() {
    }

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
    public String getName() {
        return productTypeName;
    }

    @Override
    public String toString() {
        return getName();
    }
}
