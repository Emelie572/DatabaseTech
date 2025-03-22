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
        List<ProductOption> productOptions = productRepository.getProductOptionData(selectedProduct.getProductName());

        String inputColor = productView.getColorInput();
        String inputSize = productView.getSizeInput();

        ProductOption selectedProductOption = findProductOption(inputColor, inputSize, productOptions);

        if (selectedProductOption == null) {
            productView.showErrorMessage(ErrorMessage.COLOR_NOT_FOUND);
            return null;
        }

        productView.displayMessage(ProductMessage.CHOSEN_PRODUCT + ": " + selectedProductOption);
        return selectedProductOption;
    }

    public ProductOption findProductOption(String color, String size, List<ProductOption> productOptions) {
        int sizeInt;
        try {
            sizeInt = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            return null;
        }

        for (ProductOption option : productOptions) {
            if (option.getColor().equalsIgnoreCase(color) && option.getSize() == sizeInt) {
                return option;
            }
        }
        return null;
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
