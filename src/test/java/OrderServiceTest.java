import org.example.snackpricing.models.Client;
import org.example.snackpricing.services.ClientService;
import org.example.snackpricing.services.OrderService;
import org.example.snackpricing.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private ProductService productService;
    @Mock
    private ClientService clientService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(productService, clientService);

        Client validClient = new Client();
        validClient.setId(1);
        validClient.setName("Valid Client");
        validClient.setBasicDiscount(new BigDecimal("7")); // Example basic discount
        validClient.setAdditionalDiscountLevel1(new BigDecimal("2")); // Example additional discount level 1
        validClient.setAdditionalDiscountLevel2(new BigDecimal("3")); // Example additional discount level 2
        when(clientService.getClientById(1)).thenReturn(validClient);

    }


    @Test
    void testProcessOrderWithNonExistentProductId() {

        String orderInput = "1,999=10"; // Non-existent product ID
        orderService.processOrder(orderInput);
    }

    @Test
    void testProcessOrderWithNonExistentClientId() {
        String orderInput = "999,1=10"; // Non-existent client ID
        orderService.processOrder(orderInput);
    }

    @Test
    void testProcessOrderWithZeroQuantityProduct() {
        String orderInput = "1,1=0"; // Client ID 1 orders Product ID 1 with zero quantity
        orderService.processOrder(orderInput);
    }

}
