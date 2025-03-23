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

    public List<ProductType> getProductTypes() {
        return productRepository.getProductTypeData();
    }

    public List<ProductCategory> getCategoriesByProductType(ProductType productType) {
        return productRepository.getCategoryData(productType.getProductTypeName());
    }

    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.getProductByCategoryData(category.getProductCategoryName());
    }

    public List<ProductOption> getProductOptions(Product selectedProduct) {
        return productRepository.getProductOptionData(selectedProduct.getProductName());
    }

    public ProductType chooseProductType() {
        List<ProductType> productTypes = getProductTypes();
        return chooseFromList(productTypes, ProductMessage.CHOOSE_PRODUCT_TYPE, ErrorMessage.PRODUCT_TYPE_NOT_FOUND);
    }

    public ProductCategory chooseProductCategory(ProductType productType) {
        List<ProductCategory> categories = getCategoriesByProductType(productType);
        return chooseFromList(categories, ProductMessage.CHOOSE_PRODUCT_CATEGORY, ErrorMessage.CATEGORY_NOT_FOUND);
    }

    public Product chooseProduct(ProductCategory selectedCategory) {
        List<Product> products = getProductsByCategory(selectedCategory);
        return chooseFromList(products, ProductMessage.CHOSEN_PRODUCT, ErrorMessage.PRODUCT_NOT_FOUND);
    }

    public ProductOption chooseProductOption(Product selectedProduct) {
        List<ProductOption> productOptions = getProductOptions(selectedProduct);
        if (productOptions == null || productOptions.isEmpty()) {
            handleErrorMessage(ErrorMessage.PRODUCT_OPTION_NOT_FOUND);
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_OPTION_NOT_FOUND.getMessage());
        }

        showItems(productOptions);

        String chosenColor = handleUserInput(ProductMessage.CHOOSE_COLOR);
        int chosenSize = getValidSize(ProductMessage.CHOOSE_SIZE);

        return productOptions.stream()
                .filter(option -> option.getColor().equalsIgnoreCase(chosenColor) && option.getSize() == chosenSize)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_OPTION_NOT_FOUND.name()));
    }

    private int getValidSize(ProductMessage message) {
        int attempts = 0;
        while (attempts < 3) {
            String input = handleUserInput(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                attempts++;
                handleErrorMessage(ErrorMessage.INVALID_INPUT);
            }
        }
        throw new IllegalArgumentException(ErrorMessage.TRY_AGAIN.getMessage());
    }

    public <T extends Name> T chooseFromList(List<T> items, ProductMessage message, ErrorMessage errorMessage) {
        if (items == null || items.isEmpty()) {
            handleErrorMessage(errorMessage);
            return null;
        }

        T selectedItem = null;
        while (selectedItem == null) {
            showItems(items);
            String choice = handleUserInput(message);

            if (choice == null || choice.trim().isEmpty()) {
                handleErrorMessage(errorMessage);
                continue;
            }

            selectedItem = findItem(choice, items);
            if (selectedItem == null) {
                handleErrorMessage(errorMessage);
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

    private void handleErrorMessage(ErrorMessage message) {
        productView.showErrorMessage(message);
    }

    public String handleUserInput(ProductMessage message) {
        return productView.getUserInput(message);
    }

    public void showItems(List<? extends Name> items) {
        productView.showItems(items);
    }
}
