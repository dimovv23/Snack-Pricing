package org.example.snackpricing.services;

import org.example.snackpricing.models.Client;
import org.example.snackpricing.models.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private ProductService productService;
    private ClientService clientService;

    public OrderService(ProductService productService, ClientService clientService){
        this.productService = productService;
        this.clientService = clientService;
    }

    public void processOrder(String orderInput) {
        String[] parts = orderInput.split(","); //Split the user order input to Client and ordered products
        int clientId = Integer.parseInt(parts[0]);
        Client client = clientService.getClientById(clientId);

        if(client == null){
            System.out.println("Error: Client ID " + clientId + " not found.");
            return;
        }
        System.out.println("\nClient: \t" + client.getName());
        System.out.println();
        System.out.println("Product\t\t\t\t\tQuantity\t\t\tStandard Unit Price\t\t\tPromotional Unit Price\t\t\tLine Total");

        BigDecimal totalBeforeDiscounts = BigDecimal.ZERO;
        Map<Integer, Integer> quantities = new HashMap<>();

        for (int i = 1; i < parts.length; i++) {
            String[] productInfo = parts[i].split("=");
            int productId = Integer.parseInt(productInfo[0]);
            int quantity = Integer.parseInt(productInfo[1]);
            if(quantity <=0 ){
                System.out.println("Notice: ProductID " + productId + " has zero or less quantity and will be ignored.");
                continue;
            }

            Product product = productService.getProductById(productId);
            if (product == null){
                System.out.println("Error: Product ID " + productId + " not found.");
                continue;
            }

            BigDecimal standardUnitPrice = productService.getStandardUnitPrice(product);
            BigDecimal promotionalUnitPrice = productService.getPromotionalUnitPrice(product,quantity);
            BigDecimal lineTotal = promotionalUnitPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);

            System.out.println(product.getName() + "\t\t\t" + quantity + "\t\t\t\tEUR " + standardUnitPrice + "\t\t\t\t\t"
                    + (promotionalUnitPrice.equals(standardUnitPrice) ? "" : ("EUR "+ promotionalUnitPrice))
                    + (promotionalUnitPrice.equals(standardUnitPrice) ? "\t\t\t\t\t\t\t\tEUR " : "\t\t\t\t\t\tEUR ")
                    + lineTotal.setScale(2, RoundingMode.HALF_UP));

            totalBeforeDiscounts = totalBeforeDiscounts.add(lineTotal).setScale(2,RoundingMode.HALF_UP);
            quantities.put(productId, quantity);
        }

        System.out.println();
        System.out.println("Total Before Client Discounts: \t\t\tEUR " + totalBeforeDiscounts.setScale(2, RoundingMode.HALF_UP));

        // Apply client and additional volume discounts
        BigDecimal basicDiscountRate = client.getBasicDiscount().divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        BigDecimal basicDiscountAmount = totalBeforeDiscounts.multiply(basicDiscountRate).setScale(2,RoundingMode.HALF_UP);
        BigDecimal totalAfterBasicDiscount = totalBeforeDiscounts.subtract(basicDiscountAmount).setScale(2,RoundingMode.HALF_UP);

        BigDecimal additionalDiscount = BigDecimal.ZERO;
        if (totalAfterBasicDiscount.compareTo(new BigDecimal("10000")) > 0) {
            additionalDiscount = client.getAdditionalDiscountLevel1().divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
        }
        if (totalAfterBasicDiscount.compareTo(new BigDecimal("30000")) > 0) {
            additionalDiscount = client.getAdditionalDiscountLevel2().divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
        }

        BigDecimal additionalDiscountAmount = totalAfterBasicDiscount.multiply(additionalDiscount).setScale(2,RoundingMode.HALF_UP);
        BigDecimal orderTotal = totalAfterBasicDiscount.subtract(additionalDiscountAmount).setScale(2,RoundingMode.HALF_UP);

        if (additionalDiscount.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Additional Volume Discount at " + additionalDiscount.multiply(BigDecimal.valueOf(100)) + "%: \tEUR " + additionalDiscountAmount.setScale(2, RoundingMode.HALF_UP));
        }

        System.out.println("Order Total Amount: \t\t\t\t\tEUR " + orderTotal.setScale(2, RoundingMode.HALF_UP));
        System.out.println();
    }
}
