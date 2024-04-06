package org.example;

import org.example.snackpricing.services.ClientService;
import org.example.snackpricing.services.OrderService;
import org.example.snackpricing.services.ProductService;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class SnackPricing {
    ProductService productService = new ProductService();
    ClientService clientService = new ClientService();
    OrderService orderService = new OrderService(productService, clientService);

    public static void main(String[] args) {
        SnackPricing snackPricing = new SnackPricing();
        String filePath = "src/main/resources/orders.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                snackPricing.orderService.processOrder(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
