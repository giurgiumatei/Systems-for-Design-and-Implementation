package ro.ubb.remoting.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started!");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.remoting.server.config");
    }
}
