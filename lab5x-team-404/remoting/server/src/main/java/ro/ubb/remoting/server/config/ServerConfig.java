package ro.ubb.remoting.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ro.ubb.remoting.common.domain.Client;
import ro.ubb.remoting.server.validators.*;
import ro.ubb.remoting.common.service.ClientService;
import ro.ubb.remoting.common.service.GunProviderService;
import ro.ubb.remoting.common.service.GunTypeService;
import ro.ubb.remoting.common.service.RentalService;
import ro.ubb.remoting.server.service.ClientsServiceImpl;
import ro.ubb.remoting.server.service.GunProviderServiceImpl;
import ro.ubb.remoting.server.service.GunTypeServiceImpl;
import ro.ubb.remoting.server.service.RentalServiceImpl;

@Configuration
@ComponentScan({"ro.ubb.remoting.server.repository", "ro.ubb.remoting.server.service", "ro.ubb.remoting.server.validators"})
public class ServerConfig {
    @Bean
    @Deprecated
    RmiServiceExporter rmiServiceExporterRental() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(RentalService.class);
        rmiServiceExporter.setService(rentalService());
        rmiServiceExporter.setServiceName("RentalService");
        return rmiServiceExporter;
    }

    @Bean
    RentalService rentalService() {
        return new RentalServiceImpl();
    }

//    @Bean
//    RentalRepository rentalRepository() {
//        return new RentalRepository();
//    }

    @Bean
    @Deprecated
    RmiServiceExporter rmiServiceExporterClient() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(ClientService.class);
        rmiServiceExporter.setService(clientService());
        rmiServiceExporter.setServiceName("ClientsService");
        return rmiServiceExporter;
    }

    @Bean
    ClientService clientService() {
        return new ClientsServiceImpl();
    }

//    @Bean
//    Repository<Long, Client> clientRepository() {
//        return new ClientRepository();
//    }

    /*@Bean
    Validator<Client> clientValidator() {
        return new ClientValidator();
    }*/

    @Bean
    @Deprecated
    RmiServiceExporter rmiServiceExporterGunProvider() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(GunProviderService.class);
        rmiServiceExporter.setService(gunProviderService());
        rmiServiceExporter.setServiceName("GunProviderService");
        return rmiServiceExporter;
    }

    @Bean
    GunProviderService gunProviderService() {
        return new GunProviderServiceImpl();
    }

//    @Bean
//    Repository<Long, GunProvider> gunProviderRepository() {
//        return new GunProviderRepository();
//    }

    @Bean
    @Deprecated
    RmiServiceExporter rmiServiceExporterGunType() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceInterface(GunTypeService.class);
        rmiServiceExporter.setService(gunTypeService());
        rmiServiceExporter.setServiceName("GunTypeService");
        return rmiServiceExporter;
    }

    @Bean
    GunTypeService gunTypeService() {
        return new GunTypeServiceImpl();
    }

//    @Bean
//    Repository<Long, GunType> gunTypeRepository() {
//        return new GunTypeRepository(new GunTypeValidator());
//    }
}
