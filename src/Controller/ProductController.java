package Controller;

import Model.*;
import Repository.ProductRepository;
import View.ProductView;
import Enum.ProductMessage;
import Enum.ErrorMessage;

import java.util.ArrayList;
import java.util.List;


public class ProductController {
    private final ProductRepository productRepository;
    private final ProductView productView;
    private Product selectedProduct;
    private String selectedColor;
    private int selectedSize;


    public ProductController(ProductRepository productRepository, ProductView productView) {
        this.productRepository = productRepository;
        this.productView = productView;
    }

    public void productFlow() {
        final ProductType productType = getProductType();
        ProductType selectedProductType = chooseProductType(productType);

        List<ProductCategory> productCategories = getProductCategories(selectedProductType);
        ProductCategory selectedCategory = chooseProductCategory(productCategories);

        List<Product> products = getProductsByCategory(selectedCategory);
        Product selectedProduct = chooseProduct(products);

        this.selectedProduct = selectedProduct;

        ProductOption selectedOption = getProductOption(selectedProduct);
        if (selectedOption == null) {
            return;
        }
        this.selectedColor = selectedOption.getColor();
        this.selectedSize = selectedOption.getSize();
    }


    public ProductType chooseProductType(ProductType productType) {

        productView.showProductTypes(productType);
        String selectedProductType = productView.getUserInput(ProductMessage.CHOOSE_PRODUCT_TYPE);

        while (true)
            if (productType.getProductTypeName().equalsIgnoreCase(selectedProductType)) {
                return productType;
            } else {
                productView.displayMessage(ErrorMessage.PRODUCT_TYPE_NOT_FOUND.getMessage());
                selectedProductType = productView.getUserInput(ProductMessage.CHOOSE_PRODUCT_TYPE);
            }
    }

    public ProductCategory chooseProductCategory(List<ProductCategory> productCategories) {

        ProductCategory selectedCategory = null;
        while (selectedCategory == null) {
            productView.showProductCategories(productCategories);
            final String categoryChoice = productView.getUserInput(ProductMessage.CHOOSE_PRODUCT_CATEGORY);

            if (categoryChoice == null || categoryChoice.trim().isEmpty()) {
                productView.displayMessage(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
                continue;
            }

            selectedCategory = productCategories.stream()
                    .filter(category -> category.getProductCategoryName().equalsIgnoreCase(categoryChoice))
                    .findFirst()
                    .orElse(null);

            if (selectedCategory == null) {
                productView.displayMessage(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
            }
        }

        return selectedCategory;
    }


    public Product chooseProduct(List<Product> products) {

        Product selectedProduct = null;

        while (selectedProduct == null) {
            String productChoice;
            productView.showProducts(products);
            productChoice = productView.getUserInput(ProductMessage.CHOSEN_PRODUCT);

            selectedProduct = products.stream()
                    .filter(product -> product.getProductName().equalsIgnoreCase(productChoice))
                    .findFirst()
                    .orElse(null);

            if (selectedProduct == null) {
                productView.displayMessage(ErrorMessage.PRODUCT_NOT_FOUND.getMessage());
            }
        }

        return selectedProduct;
    }


    private ProductOption chooseColorOption(List<ProductOption> productOptions) {
        productOptions.stream()
                .map(ProductOption::getColor)
                .distinct()
                .forEach(color -> productView.displayMessage(color));

        ProductOption selectedOption = null;

        while (selectedOption == null) {
            String colorChoice = productView.getUserInput(ProductMessage.CHOOSE_COLOR);

            if (colorChoice == null || colorChoice.trim().isEmpty()) {
                productView.displayMessage(ErrorMessage.COLOR_NOT_FOUND.getMessage());
            } else {
                selectedOption = productOptions.stream()
                        .filter(option -> option.getColor().equalsIgnoreCase(colorChoice))
                        .findFirst()
                        .orElse(null);

                if (selectedOption == null) {
                    productView.displayMessage(ErrorMessage.COLOR_NOT_FOUND.getMessage());
                }
            }
        }

        return selectedOption;
    }

    private ProductOption chooseSizeOption(List<ProductOption> productOptions) {
        productOptions.stream()
                .map(option -> String.valueOf(option.getSize()))
                .distinct()
                .forEach(size -> productView.displayMessage(size));

        ProductOption selectedOption = null;

        while (selectedOption == null) {
            String sizeChoice = productView.getUserInput(ProductMessage.CHOOSE_SIZE);

            if (sizeChoice == null || sizeChoice.trim().isEmpty()) {
                productView.displayMessage(ErrorMessage.SIZE_NOT_FOUND.getMessage());
            } else {
                try {
                    final int size = Integer.parseInt(sizeChoice);

                    selectedOption = productOptions.stream()
                            .filter(option -> option.getSize() == size)
                            .findFirst()
                            .orElse(null);

                    if (selectedOption == null) {
                        productView.displayMessage(ErrorMessage.SIZE_NOT_FOUND.getMessage());
                    }
                } catch (NumberFormatException e) {
                    productView.displayMessage(ErrorMessage.SIZE_NOT_FOUND.getMessage());
                }
            }
        }

        return selectedOption;
    }


    public ProductOption getProductOption(Product product) {
        if (isProductOptionSelected()) {
            return createProductOption(getSelectedColor(), getSelectedSize());
        }
        return getUserSelectedProductOption(product);
    }

    private boolean isProductOptionSelected() {
        return getSelectedColor() != null && getSelectedSize() != 0;
    }

    private ProductOption createProductOption(String color, int size) {
        ProductOption productOption = new ProductOption();
        productOption.setColor(color);
        productOption.setSize(size);
        return productOption;
    }

    private ProductOption getUserSelectedProductOption(Product product) {
        List<ProductOption> productOptions = getProductOptions(product);

        ProductOption selectedColorOption = chooseColorOption(productOptions);
        if (selectedColorOption == null) {
            productView.displayMessage(ProductMessage.CHOOSE_COLOR.getMessage());
            return null;
        }

        List<ProductOption> sizeOptions = filterSizeOptionsByColor(productOptions, selectedColorOption);
        ProductOption selectedSizeOption = chooseSizeOption(sizeOptions);
        if (selectedSizeOption == null) {
            productView.displayMessage(ProductMessage.CHOOSE_SIZE.getMessage());
            return null;
        }

        return createProductOption(selectedColorOption.getColor(), selectedSizeOption.getSize());
    }

    private List<ProductOption> filterSizeOptionsByColor(List<ProductOption> options, ProductOption colorOption) {
        return options.stream()
                .filter(option -> option.getColor().equalsIgnoreCase(colorOption.getColor()))
                .toList();
    }



    private ProductType getProductType() {
        return productRepository.getProductTypeData();
    }

    private List<ProductCategory> getProductCategories(ProductType productType) {
        List<ProductCategory> productCategories = productRepository.getCategoryData(productType.getProductTypeName());

        if (productCategories == null || productCategories.isEmpty()) {
            productView.displayMessage(ErrorMessage.CATEGORY_NOT_FOUND.getMessage());
            return new ArrayList<>();
        }

        return productCategories;
    }

    List<Product> getProductsByCategory(ProductCategory category) {
        List<Product> products = productRepository.getProductByCategoryData(category.getProductCategoryName());

        if (products == null || products.isEmpty()) {
            productView.displayMessage(ErrorMessage.PRODUCT_NOT_FOUND.getMessage());
            return new ArrayList<>();
        }
        return products;
    }

    List<ProductOption> getProductOptions(Product product) {
        List<ProductOption> productOptions = productRepository.getProductOptionData(product.getProductName());

        if (productOptions == null || productOptions.isEmpty()) {
            productView.displayMessage(ErrorMessage.PRODUCT_OPTION_NOT_FOUND.getMessage());
            return new ArrayList<>();
        }
        return productOptions;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public String getSelectedColor() {
        return this.selectedColor;
    }

    public int getSelectedSize() {
        return this.selectedSize;
    }

}