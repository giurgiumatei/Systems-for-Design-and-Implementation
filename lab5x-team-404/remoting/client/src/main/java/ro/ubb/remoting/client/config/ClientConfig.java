package ro.ubb.remoting.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.ubb.remoting.common.service.ClientService;
import ro.ubb.remoting.common.service.GunProviderService;
import ro.ubb.remoting.common.service.GunTypeService;
import ro.ubb.remoting.common.service.RentalService;
//for attenace
@Configuration
@ComponentScan({"ro.ubb.remoting.client.service", "ro.ubb.remoting.client.ui"})
public class ClientConfig {

    @Bean
    @Deprecated
    RmiProxyFactoryBean rmiProxyFactoryBeanClient() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ClientsService");
        rmiProxyFactoryBean.setServiceInterface(ClientService.class);
        return rmiProxyFactoryBean;
    }
    @Bean
    @Deprecated
    RmiProxyFactoryBean rmiProxyFactoryBeanGunProvider() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/GunProviderService");
        rmiProxyFactoryBean.setServiceInterface(GunProviderService.class);
        return rmiProxyFactoryBean;
    }

    @Bean
    @Deprecated
    RmiProxyFactoryBean rmiProxyFactoryBeanGunType() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/GunTypeService");
        rmiProxyFactoryBean.setServiceInterface(GunTypeService.class);
        return rmiProxyFactoryBean;
    }

    @Bean
    @Deprecated
    RmiProxyFactoryBean rmiProxyFactoryBeanRental() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/RentalService");
        rmiProxyFactoryBean.setServiceInterface(RentalService.class);
        return rmiProxyFactoryBean;
    }

   /* @Bean
    ClientConsole clientConsole() {
        return new ClientConsole();
    }*/

    /*@Bean
    ClientService clientsServiceClient() {
        return new ClientsServiceClient();
    }

    @Bean
    GunProviderService gunProviderServiceClient() {
        return new GunProviderServiceClient();
    }

    @Bean
    GunTypeService gunTypeServiceClient() {
        return new GunTypeServiceClient();
    }

    @Bean
    RentalService rentalServiceClient() {
        return new RentalServiceClient();
    }*/
}
