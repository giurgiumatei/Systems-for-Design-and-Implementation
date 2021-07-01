package ro.ubb.remoting.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.remoting.client.ui.ClientConsole;
//for attendance only
public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.remoting.client.config");

        ClientConsole clientConsole = context.getBean(ClientConsole.class);
        clientConsole.runConsole();
        
        System.out.println("bye");
    }
}
