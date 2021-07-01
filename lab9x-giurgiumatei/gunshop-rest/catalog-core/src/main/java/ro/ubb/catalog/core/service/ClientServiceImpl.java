package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.model.validators.ClientValidator;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.GunTypeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    public static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientValidator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GunTypeRepository gunTypeRepository;

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
        updateClient.setAccount(client.getAccount());
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


    @Transactional
    public Optional<Client> updateClientPrices(Long clientId, Map<Long, Integer> prices) {
        logger.trace("updateClientPrices: clientId={}, prices={}", clientId, prices);

        throw new RuntimeException("not yet implemented");
    }

    @Transactional
    public Rental addRental(Rental rental)
    {
        logger.trace("addRental - method entered; rental = {}", rental);
        Client client = clientRepository.findByName(rental.getClient().getName()).get(0);
        GunType gunType = gunTypeRepository.findByName(rental.getGunType().getName()).get(0);
        Rental rentalToBeAdded = new Rental(client,gunType, rental.getPrice());
        clientRepository.findByName(rental.getClient().getName()).get(0).addRental(rentalToBeAdded);
        logger.trace("addRental - method finished; rental = {}", rentalToBeAdded);
        return rentalToBeAdded;

    }
}
