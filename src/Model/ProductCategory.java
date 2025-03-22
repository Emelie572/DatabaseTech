package Model;

import Interface.Name;

public class ProductCategory implements Name {

    private int productCategoryId;
    private String productCategoryName;


    public ProductCategory() {
    }

    public ProductCategory(int productCategoryId, String productCategoryName) {
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    @Override
    public String getName() {
        return productCategoryName;
    }

    @Override
    public String toString() {
        return getName();
    }
}

