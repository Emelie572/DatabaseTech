public class ProductOption {

    private int productOptionId;
    private int size;
    private String color;
    private int stock;

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

    public ProductOption(int productOptionId, int size, String color, int stock) {
        this.productOptionId = productOptionId;
        this.size = size;
        this.color = color;
        this.stock = stock;


    }
}
