package ro.ubb.socket.server.tcp;

import domain.*;
import domain.validators.GunShopException;
import message.Message;
import service.ClientService;
import service.GunProviderService;
import service.GunTypeService;
import service.RentalService;
import utils.Factory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MessageHandler {

    ClientService clientService;
    GunProviderService gunProviderService;
    GunTypeService gunTypeService;
    RentalService rentalService;

    // for Client
    public MessageHandler(ClientService clientService, GunProviderService gunProviderService, GunTypeService gunTypeService, RentalService rentalService) {
        this.clientService = clientService;
        this.gunProviderService = gunProviderService;
        this.gunTypeService = gunTypeService;
        this.rentalService = rentalService;
    };

    public Message addClient(Message request) {
        Client client = Factory.messageToClient(request.getBody());
        try {
            if (clientService.addClient(client).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "duplicate client");
        } catch (GunShopException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message removeClient(Message request) {
        try {
            clientService.removeClient(Long.parseLong(request.getBody())).get();
            return new Message("ok", "ok");
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateClient(Message request) {
        Client client = Factory.messageToClient(request.getBody());
        try {
            clientService.updateClient(client).get();
            return new Message("ok", "ok");
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getAllClients(Message request) {
        try {
            Set<Client> clientSet = clientService.getAllClients().get();
            String messageBody = clientSet.stream()
                                        .map(Factory::clientToLine)
                                        .reduce("", (a, b) -> a + b + System.lineSeparator());
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    //for gun provider

    public Message addGunProvider(Message request) {
        GunProvider gunProvider = Factory.messageToGunProvider(request.getBody());
        try {
            gunProviderService.addGunProvider(gunProvider).get();
            return new Message("ok", "ok");
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message removeGunProvider(Message request) {
        try {
            gunProviderService.removeGunProvider(Long.parseLong(request.getBody())).get();
            return new Message("ok", "ok");
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateGunProvider(Message request) {
        GunProvider gunProvider = Factory.messageToGunProvider(request.getBody());
        try {
            gunProviderService.updateGunProvider(gunProvider);
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getAllGunProviders(Message request) {
        try {
            Set<GunProvider> gunProviderSet = gunProviderService.getAllGunProviders().get();
            String messageBody = gunProviderSet.stream()
                                        .map(gp -> Factory.gunProviderToLine(gp))
                                        .reduce("", (a, b) -> a + b + "\n");
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    // for gun type

    public Message addGunType(Message request) {
        GunType gunType = Factory.messageToGunType(request.getBody());
        try {
            gunTypeService.addGunType(gunType);
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message removeGunType(Message request) {
        try {
            gunTypeService.removeGunType(Long.parseLong(request.getBody()));
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message updateGunType(Message request) {
        GunType gunType = Factory.messageToGunType(request.getBody());
        try {
            gunTypeService.updateGunType(gunType);
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message getAllGunTypes(Message request) {
        try {
            Set<GunType> gunTypeSet = gunTypeService.getAllGunTypes().get();
            String messageBody = gunTypeSet.stream()
                                        .map(Factory::gunTypeToLine)
                                        .reduce("", (a, b) -> a + b + "\n");
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getMessage());
        }
    }

    //for rentals

    public Message addRental(Message request) {
        Rental rental = Factory.messageToRental(request.getBody());
        try {
            rentalService.addRental(rental);
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message removeRental(Message request) {
        try {
            rentalService.removeRental(Factory.messageToPair(request.getBody())).get();
            return new Message("ok", "ok");
        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message updateRental(Message request) {
        Rental rental = Factory.messageToRental(request.getBody());
        try {
            rentalService.updateRental(rental);
            return new Message("ok", "ok");
        } catch (RuntimeException e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message getAllRentals(Message request) {
        try {
            Set<GunType> gunTypeSet = gunTypeService.getAllGunTypes().get();
            Set<Rental> rentalSet = rentalService.getAllRentals().get();
            String messageBody = rentalSet.stream()
                                        .map(Factory::rentalToLine)
                                        .reduce("", (a, b) -> a + b + "\n");
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message getAllClientsBornBefore(Message request) {
        try {
            Set<Client> filtered = clientService.getAllClientsBornBefore(Factory.messageToDate(request.getBody())).get();
            String messageBody = filtered.stream()
                    .map(Factory::clientToLine)
                    .reduce("", (a, b) -> a + b + "\n");
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message getGunsFilteredByCategory(Message request) {
        try {
            List<GunType> filtered = gunTypeService.filterGunTypesByCategory(Category.valueOf(request.getBody())).get();
            String messageBody = filtered.stream()
                    .map(Factory::gunTypeToLine)
                    .reduce("", (a, b) -> a + b + "\n");
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getMessage());
        }
    }

    public Message getMostRentedGunType(Message request) {
        try {
            var mostRented = rentalService.getMostRentedGunType().get();
            String messageBody = Factory.gunTypeToLine(mostRented);
            return new Message("ok", messageBody);
        } catch (Exception e) {
            return new Message("error", e.getMessage());
        }
    }
}
