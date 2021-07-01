package ro.ubb.springjpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.springjpa.model.Client;
import ro.ubb.springjpa.model.validators.ClientValidator;
import ro.ubb.springjpa.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientValidator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public void addClient(Client client) {
        log.trace("addClient - method entered client={}", client);
        validator.validate(client);
        clientRepository.save(client);
        log.trace("addClient - client={} saved", client);
    }

    @Override
    @Transactional
    public void deleteClient(Client client) {
        log.trace("deleteClient - method entered client={}", client);
        validator.validate(client);
        clientRepository.delete(client);
        log.trace("deleteClient - client={} deleted", client);
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
        log.trace("updateClient - method entered client={}", client);
        validator.validate(client);
        clientRepository.findById(client.getId())
                .ifPresent(
                        clientFound ->{
                          //  clientFound.setId(client.getId());
                            clientFound.setName(client.getName());
                            clientFound.setDateOfBirth(client.getDateOfBirth());
                            log.trace("updateClient - updated client={}", client);
                        }
                );
    }

    @Override
    public List<Client> getAllClients() {
        log.trace("getAllClients --- method entered");
        List<Client> result = clientRepository.findAll();
        log.trace("getAllClients: result={}", result);
        return result;
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Client> getAllClientsBornBefore(LocalDate date) {
        log.trace("filterBornBefore - method entered date={}", date);
        var result = clientRepository.findAll()
                .stream()
                .filter(client -> client.getDateOfBirth().isBefore(date))
                .collect(Collectors.toSet());
        log.trace("getAllClientsBornBefore: result={}", result);
        return result;
    }


}
