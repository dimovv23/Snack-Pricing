import org.example.snackpricing.services.ProductService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductServiceTest {

    @Test
    void testGetNonExistentProductById() throws Exception {
        ProductService productService = new ProductService();
        // Assuming there is no product with ID 999
        assertNull(productService.getProductById(999), "Should return error message from slf4j");
    }
}
