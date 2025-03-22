package Controller;

import Interface.Name;
import Model.*;
import Repository.ProductRepository;
import View.ProductView;
import Enum.ProductMessage;
import Enum.ErrorMessage;
import java.util.List;

public class ProductController {
    private final ProductRepository productRepository;
    private final ProductView productView;

    public ProductController(ProductRepository productRepository, ProductView productView) {
        this.productRepository = productRepository;
        this.productView = productView;
    }

    public List<ProductOption> getProductOptions(Product selectedProduct) {
        return productRepository.getProductOptionData(selectedProduct.getProductName());
    }

    public ProductType chooseProductType() {
        productView.showProductTypes(getProductType());
        String selectedProductType = getUserInput(ProductMessage.CHOOSE_PRODUCT_TYPE);

        while (!isValidProductType(selectedProductType)) {
            productView.showErrorMessage(ErrorMessage.PRODUCT_TYPE_NOT_FOUND);
            selectedProductType = getUserInput(ProductMessage.CHOOSE_PRODUCT_TYPE);
        }

        return getProductType();
    }

    public ProductCategory chooseProductCategory(ProductType productType) {
        List<ProductCategory> productCategories = productRepository.getCategoryData(productType.getProductTypeName());
        return chooseFromList(productCategories, ProductMessage.CHOOSE_PRODUCT_CATEGORY, ErrorMessage.CATEGORY_NOT_FOUND);
    }

    public Product chooseProduct(ProductCategory selectedCategory) {
        List<Product> products = productRepository.getProductByCategoryData(selectedCategory.getProductCategoryName());
        return chooseFromList(products, ProductMessage.CHOSEN_PRODUCT, ErrorMessage.PRODUCT_NOT_FOUND);
    }

    public ProductOption chooseProductOption(Product selectedProduct) {
        List<ProductOption> productOptions = getProductOptions(selectedProduct);

        productView.showItems(productOptions);

        String chosenColor = getUserInput(ProductMessage.CHOOSE_COLOR);
        int chosenSize = getValidSize(ProductMessage.CHOOSE_SIZE);

        return productOptions.stream()
                .filter(option -> option.getColor().equalsIgnoreCase(chosenColor) && option.getSize() == chosenSize)
                .findFirst()
                .orElseGet(() -> {
                    productView.showErrorMessage(ErrorMessage.PRODUCT_OPTION_NOT_FOUND);
                    return null;
                });
    }

    private int getValidSize(ProductMessage message) {
        while (true) {
            String input = getUserInput(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                productView.showErrorMessage(ErrorMessage.INVALID_INPUT);
            }
        }
    }

    public <T extends Name> T chooseFromList(List<T> items, ProductMessage message, ErrorMessage errorMessage) {
        T selectedItem = null;
        while (selectedItem == null) {
            productView.showItems(items);
            String choice = getUserInput(message);

            if (choice == null || choice.trim().isEmpty()) {
                productView.showErrorMessage(errorMessage);
                continue;
            }

            selectedItem = findItem(choice, items);
            if (selectedItem == null) {
                productView.showErrorMessage(errorMessage);
            }
        }
        return selectedItem;
    }

    public <T extends Name> T findItem(String choice, List<T> items) {
        for (T item : items) {
            if (item.getName().equalsIgnoreCase(choice)) {
                return item;
            }
        }
        return null;
    }

    public ProductOption finalProduct() {
        ProductType productType = chooseProductType();
        ProductCategory selectedProductCategory = chooseProductCategory(productType);
        Product selectedProduct = chooseProduct(selectedProductCategory);
        return chooseProductOption(selectedProduct);
    }

    public String getUserInput(ProductMessage message) {
        return productView.getUserInput(message);
    }

    public boolean isValidProductType(String selectedProductType) {
        return getProductType().getProductTypeName().equalsIgnoreCase(selectedProductType);
    }

    public ProductType getProductType() {
        return productRepository.getProductTypeData();
    }
}
