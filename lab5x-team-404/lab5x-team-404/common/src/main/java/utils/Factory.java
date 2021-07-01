package utils;

import domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Factory
{

    public static String clientToLine(Client client)
    {
        return client.getId() + "," + client.getName() + "," + client.getDateOfBirth().getDayOfMonth() + "/" + client.getDateOfBirth().getMonthValue() + "/" + client.getDateOfBirth().getYear();

    }

    public static String gunProviderToLine(GunProvider gunProvider)
    {

        return gunProvider.getId() + "," + gunProvider.getName() + "," + gunProvider.getSpeciality() + "," + gunProvider.getReputation();

    }

    public static String gunTypeToLine(GunType gunType)
    {
        return gunType.getId() + "," + gunType.getName() + "," + gunType.getCategory() + "," + gunType.getGunProviderID();
    }

    public static String rentalToLine(Rental rental)
    {
        return  rental.getId().getFirst() + "," + rental.getId().getSecond() + "," + rental.getPrice();
    }

    public static Client messageToClient(String message)
    {
        if(message.length() == 0)
        {
            return new Client();
        }

        String[] tokens = message.split(",");

        if(tokens.length == 0)
        {
            return new Client();
        }

        var id = Long.parseLong(tokens[0]);
        var name = tokens[1];
        var date=tokens[2];
        var formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        var dateOfBirth= LocalDate.parse(date, formatter);

        return new Client(id,name,dateOfBirth);

    }

    public static GunProvider messageToGunProvider(String message)
    {
        String[] tokens = message.split(",");

        var id = Long.parseLong(tokens[0]);
        var name = tokens[1];
        var speciality = tokens[2];
        var reputation = Integer.parseInt(tokens[3]);

        return new GunProvider(id,name,speciality,reputation);

    }

    public static GunType messageToGunType(String message)
    {
        String[] tokens = message.split(",");

        var id = Long.parseLong(tokens[0]);
        var name = tokens[1];
        var category = Category.valueOf(tokens[2]);
        var gunProviderID = Long.parseLong(tokens[3]);

        return new GunType(id,name,category,gunProviderID);

    }

    public static Pair<Long, Long> messageToPair(String message) {
        String substring = message.substring(1, message.length() - 1);
        String[] tokens = substring.split(",");
        return new Pair<Long,Long>(Long.parseLong(tokens[0]),Long.parseLong(tokens[1]));
    }

    public static Rental messageToRental(String message)
    {
        String[] tokens = message.split(",");

        var id = new Pair<Long,Long>(Long.parseLong(tokens[0]),Long.parseLong(tokens[1]));
        var price = Integer.parseInt(tokens[2]);

        return new Rental(id,price);
    }

    public static LocalDate messageToDate(String message) {
        var formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        var dateOfBirth= LocalDate.parse(message, formatter);
        return dateOfBirth;
    }


}
