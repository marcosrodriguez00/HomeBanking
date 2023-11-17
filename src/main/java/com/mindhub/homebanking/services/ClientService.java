package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    List<ClientDTO> getAllClientDTO();

    Client getClientById(Long id);

    ClientDTO getClientDTOById(Long id);

    Client getClientByEmail(String email);

    void saveClient(Client client);
}