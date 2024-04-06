import org.example.snackpricing.services.ClientService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientServiceTest {

    @Test
    void testGetNonExistentClientById() throws Exception {
        ClientService clientService = new ClientService();
        // Assuming there is no client with ID 999
        assertNull(clientService.getClientById(999), "Should return error message from slf4j");
    }
}
