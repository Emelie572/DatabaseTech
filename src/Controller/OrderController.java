package Controller;
import Model.*;
import Repository.OrderRepository;
import Repository.ProductRepository;
import View.OrderView;
import java.util.List;
import Enum.ErrorMessage;
import Enum.OrderMessage;
import Enum.OrderStatus;
import Enum.ProductMessage;

public class OrderController {

    private final ProductController productController;
    private final OrderRepository orderRepository;
    private final OrderView orderView;

    public OrderController(ProductController productController, OrderRepository orderRepository, OrderView orderView) {
        this.productController = productController;
        this.orderRepository = orderRepository;
        this.orderView = orderView;
    }

    public Integer getOrderId(int customerId) {
        List<CustomerOrder> customerOrders = orderRepository.getCustomerOrderData();

        for (CustomerOrder order : customerOrders) {
            if (order.getCustomerId() == customerId && OrderStatus.ACTIVE_ORDER.getMessage().equals(order.getOrderStatus())) {
                return order.getOrderId();
            }
        }
        return null;
    }


    public void prepareOrder() {
        List<CustomerOrder> customer = orderRepository.getCustomerOrderData();
        Product selectedProduct = productController.getSelectedProduct();
        ProductOption selectedProductOption = productController.getProductOption(selectedProduct);

        if (selectedProductOption == null) {
            orderView.displayMessage(ProductMessage.CHOOSE_PRODUCT.getMessage());
            return;
        }

        Integer orderId = getOrderId(customer.get(0).getCustomerId());
        addToCart(customer.get(0).getCustomerId(), orderId, selectedProductOption.getProductOptionId());
    }


    public void addToCart(int customerId, Integer orderId, int productOptionId) {
        orderRepository.addToCart(customerId, orderId, productOptionId);
        orderView.displayMessage(OrderMessage.ORDER_ADD_UPDATE.getMessage());
    }


    public void askContinueShopping() {
        while (true) {
            final String input = orderView.getUserInput(OrderMessage.ORDER_CONTINUE_SHOPPING);

            if (input.equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage())) {
                productController.productFlow();
                break;

            } else if (input.equalsIgnoreCase(OrderMessage.ORDER_READY_TO_PAY.getMessage())) {
                handlePayment();
                break;

            } else {
                orderView.displayMessage(ErrorMessage.TRY_AGAIN.getMessage());
            }
        }
    }

    public void handlePayment() {
        final String inputPay = orderView.getUserInput(OrderMessage.ORDER_PAY);
        if (inputPay.equalsIgnoreCase(OrderMessage.ANSWER_YES.getMessage())) {
            orderView.displayMessage(OrderMessage.ORDER_PAY_UPDATE.getMessage());
        } else {
            orderView.displayMessage(OrderMessage.ORDER_PAYMENT_DENIED.getMessage());
        }
    }
}