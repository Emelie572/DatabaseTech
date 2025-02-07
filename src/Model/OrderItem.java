package Model;


public class OrderItem {

    private int orderItemId;
    private int quantity;
    private int productOptionId;
    private int orderId;


    public OrderItem() {
    }

    public OrderItem(int orderItemId, int quantity, int productOptionId, int orderId) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.productOptionId = productOptionId;
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(int productOptionId) {
        this.productOptionId = productOptionId;
    }


    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}