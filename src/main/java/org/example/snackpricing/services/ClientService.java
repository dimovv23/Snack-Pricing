package org.example.snackpricing.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.snackpricing.models.Client;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;

public class ClientService {
    private HashMap<Integer, Client> clients = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public  ClientService(){
       try {
           loadClients();
       }catch (IOException e){
           throw new RuntimeException(e);
       }
    }

    public Client getClientById(int id){
        Client client = clients.get(id);
        if(client == null){
            logger.error("Client with ID {} not found", id);
        }
        return client;
    }

    public void loadClients() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("clients.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Cannot find 'clients.json' in resources.");
        }
        //deserialization of the JSON
        List<Client> clientList = mapper.readValue(inputStream, new TypeReference<>() {});
        for (Client client : clientList) {
            clients.put(client.getId(), client);
        }
        inputStream.close();
    }

}
