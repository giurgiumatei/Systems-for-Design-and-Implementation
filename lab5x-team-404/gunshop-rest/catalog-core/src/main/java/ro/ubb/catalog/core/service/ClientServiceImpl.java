package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.ClientValidator;
import ro.ubb.catalog.core.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    public static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientValidator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        logger.trace("getAllClients - method entered");
        return clientRepository.findAll();
    }

    @Override
    public Client addClient(Client client) {
        logger.trace("addClient - method entered; client = {}", client);
        validator.validate(client);
        Client clientAdded = clientRepository.save(client);
        logger.trace("addClient - method finished; client = {}", clientAdded);
        return clientAdded;
    }

    @Override
    public void deleteClient(Long id) {
        logger.trace("deleteClient - method entered; client = {}", clientRepository.findById(id));
        clientRepository.deleteById(id);
        logger.trace("deleteClient - method finished");
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {
        logger.trace("updateClient - method entered; client = {}", client);
        validator.validate(client);
        Client updateClient = clientRepository.findById(client.getId()).orElseThrow();
        updateClient.setName(client.getName());
        updateClient.setDateOfBirth(client.getDateOfBirth());
        logger.trace("updateClient - method finished; client = {}", updateClient);
        return client;
    }

    @Override
    public Client getClientById(Long id) {
        logger.trace("getClientById - method entered={}", id);
        Client clientById = clientRepository.findById(id).orElseThrow();
        logger.trace("getClientById - method finished; result={}", clientById);
        return clientById;
    }

    @Override
    public List<Client> getAllClientsBornBefore(LocalDate date) {
        logger.trace("getAllClientsBornBefore - method entered; date={}", date);
        List<Client> clientsBornBefore = clientRepository.findClientsByDateOfBirthBefore(date);
        logger.trace("getAllClientsBornBefore - method finished; result={}", clientsBornBefore);
        return clientsBornBefore;
    }
}
