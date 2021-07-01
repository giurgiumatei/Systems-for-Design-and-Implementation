package ro.ubb.springjpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.springjpa.ui.Console;

/*
for attendance
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.springjpa.config");
        Console console = context.getBean(Console.class);
        console.runConsole();
    }
}
