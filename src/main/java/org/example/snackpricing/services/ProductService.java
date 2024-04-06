package org.example.snackpricing.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.snackpricing.models.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductService {
    private Map<Integer, Product> products = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(){
        try {
            loadProducts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(int id){
        Product product = products.get(id);
        if(product == null){
            logger.error("Product with ID {} not found", id);
        }
        return product;
    }
    public void loadProducts() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("products.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Cannot find 'products.json' in resources.");
        }
        List<Product> productList = mapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        for (Product product : productList) {
            products.put(product.getId(), product);
        }
        inputStream.close();
    }
    public BigDecimal getStandardUnitPrice(Product product) {
        BigDecimal unitCost = product.getUnitCost();
        BigDecimal markup = product.getMarkup();
        if (product.isMarkupPercentage()) { // Check if the markup is fixed value or percentage
            BigDecimal convertedPercentageMarkup = markup.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            return unitCost.add(unitCost.multiply(convertedPercentageMarkup)).setScale(2, RoundingMode.HALF_UP);
        } else { // Calculating unit price for product with fixed value markup
            return unitCost.add(markup).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getPromotionalUnitPrice(Product product, int quantity) {
        BigDecimal standardPrice = getStandardUnitPrice(product);
        String promotion = product.getPromotion();
        if ("none".equals(promotion)) {
            return standardPrice;
        } else if ("30% off".equals(promotion)) {
            return standardPrice.multiply(new BigDecimal("0.7")).setScale(5, RoundingMode.HALF_UP);
        } else if ("Buy 2 get 3rd free".equals(promotion) && quantity >= 3) {
            int freeItems = quantity / 3;
            BigDecimal totalCost = standardPrice.multiply(new BigDecimal(quantity - freeItems));
            return totalCost.divide(new BigDecimal(quantity), 5, RoundingMode.HALF_UP);
        }
        return standardPrice; // Default to standard price if no promotion matches
    }
}
