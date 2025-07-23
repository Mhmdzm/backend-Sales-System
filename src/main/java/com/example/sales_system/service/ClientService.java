package com.example.salessystem.service;

import com.example.salessystem.dto.ClientDTO;
import com.example.salessystem.model.Client;
import com.example.salessystem.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ClientDTO createClient(ClientDTO dto) {
        Client client = Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .mobile(dto.getMobile())
                .build();
        return toDTO(clientRepository.save(client));
    }

    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setMobile(dto.getMobile());
        return toDTO(clientRepository.save(client));
    }

    private ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .mobile(client.getMobile())
                .build();
    }
}
