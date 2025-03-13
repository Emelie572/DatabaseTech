package Model;

public class ProductOption {

    private int productOptionId;
    private int size;
    private String color;
    private int stock;
    private int productId;


    public ProductOption() {
    }

    public ProductOption(int productOptionId, int size, String color, int stock, int productId) {
        this.productOptionId = productOptionId;
        this.size = size;
        this.color = color;
        this.stock = stock;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(int productOptionId) {
        this.productOptionId = productOptionId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return "Färg: " + color + " - Storlek: " + size;
    }
}

